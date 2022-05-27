(ns plantuml-clj.core
  (:require
    [clojure.string :as str]
    [me.raynes.fs :as fs])
  (:import
    (net.sourceforge.plantuml
      FileFormat
      FileFormatOption
      GeneratedImage
      ISourceFileReader
      SourceFileReader)
    (net.sourceforge.plantuml.preproc Defines)))

(def default-sources "src/plantuml/*.puml")

(def default-file-format :png)

(def format->file-format
  {:eps FileFormat/EPS
   :eps:txt FileFormat/EPS_TEXT
   ; :pdf FileFormat/PDF
   :png FileFormat/PNG
   :svg FileFormat/SVG
   :txt FileFormat/ATXT
   :utxt FileFormat/UTXT})

(defn- log [& messages] (println (str/join messages)))

(defn- abs-file [fname]
  (when fname
    (doto
      (fs/file (.getAbsolutePath (fs/file fname)))
      (.mkdirs))))

(defn- clean-path [p]
  (when-not (nil? p)
    (if (.startsWith (System/getProperty "os.name") "Windows")
      (str/replace p "/" "\\")
      (str/replace p "\\" "/"))))

(defn ->file-format-or-default [fmt]
  (let [format (some-> fmt
                       name
                       str/lower-case 
                       keyword
                       format->file-format)]
    (when-not format
      (log "WARNING: Unsupported format '" fmt "', fallback to " default-file-format))
    (or format (format->file-format default-file-format))))

(defn input-glob [dir]
  (-> dir 
      clean-path
      fs/glob))

(defn- create-reader ^ISourceFileReader [input output fformat]
  (let [in (abs-file input)
        out (abs-file output)
        defines (Defines.)
        fmt (FileFormatOption. fformat)
        config []
        charset nil]
    (SourceFileReader. defines in out config charset fmt)))

(defn process-file [in out fmt]
  (try
    (let [reader (create-reader in out fmt)
          images (.getGeneratedImages reader)]
      (doseq [^GeneratedImage image images]
        (log "Processed file: " (.getDescription image) " format: " fmt))
      images)
    (catch Throwable t
      (throw (ex-info (str "Can not render file " in " with file format " fmt)
                      {:in in :out out :fmt fmt}
                      t)))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn do-plantuml [{:keys [input-dir output-dir fmt]
                    :or {input-dir default-sources}}]
  (prn {:input-dir input-dir
        :fmt fmt
        :output-dir output-dir})
  (let [inputs (input-glob input-dir) 
        fmt (->file-format-or-default fmt)
        output (str output-dir)] 
    (doseq [input inputs]
      (process-file input output fmt))))

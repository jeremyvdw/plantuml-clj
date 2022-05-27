(ns plantuml-clj.core-test
  (:require [me.raynes.fs :as fs]
            [plantuml-clj.core :as core]
            [clojure.test :refer [deftest is]])
  (:import (net.sourceforge.plantuml FileFormat)))

(def ^:private default-output "test-out")

(defn- clear-output[]
  (fs/delete-dir default-output))

(defn- check-formats [& names]
  (let [formats (distinct (map core/->file-format-or-default names))
        eq (= 1 (count formats))]
    (if eq
      (first formats)
      (throw (Exception. "Incorrect file-format function")))))

(deftest check-available-file-formats
  (is (= (check-formats :eps :EPS "eps" "EPS") FileFormat/EPS))
  (is (= (check-formats :eps:txt "eps:txt") FileFormat/EPS_TEXT))
  (is (= (check-formats :png "png") FileFormat/PNG))
  (is (= (check-formats :txt "txt") FileFormat/ATXT))
  (is (= (check-formats :utxt "utxt") FileFormat/UTXT))
  (is (= (check-formats :svg "SVG") FileFormat/SVG)))
  ; (is (= (check-formats :pdf "PDF") FileFormat/PDF)))

(defn check-process
  ([u f] (try
           clear-output
           (core/process-file u default-output f)
           (finally (clear-output))))
  ([u] (let [file (str "example/doc/" u)
             formats (vals core/format->file-format)]
         (every? #(check-process file %) formats))))

(deftest check-plantuml-processor
  (is (= (check-process "example-01.puml") true))
  (is (= (check-process "example-02.puml") true))
  (is (= (check-process "example-03.puml") true))
  (is (= (check-process "example-04.puml") true)))

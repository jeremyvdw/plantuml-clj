plantuml-clj
=============

[![tests](https://github.com/jeremyvdw/plantuml-clj/actions/workflows/clojure.yml/badge.svg?branch=main)](https://github.com/jeremyvdw/plantuml-clj/actions/workflows/clojure.yml)

[PlantUML](http://plantuml.sourceforge.net) is an open-source tool that uses simple textual descriptions to draw UML diagrams. Diagrams are defined using a simple and intuitive language.

[plantuml-clj](https://github.com/jeremyvdw/plantuml-clj) is a plugin for generating UML diagrams using PlantUML.

Pre-requirements
================

Install [Graphviz](http://www.graphviz.org) to use plantuml-clj plugin. PlantUML should be working with any version of GraphViz, starting with 2.26.3

* [Mac OS X](http://www.graphviz.org/Download_macos.php):
```bash
brew install graphviz
```
* [Ubuntu](http://www.graphviz.org/Download_linux_ubuntu.php):
```bash
sudo apt-get install graphviz
```
* [Fedora](http://www.graphviz.org/Download_linux_fedora.php):
```bash
yum list available 'graphviz*'
yum install 'graphviz*'
```
* [RHEL or CentOS](http://www.graphviz.org/Download_linux_rhel.php)
* [Solaris](http://www.graphviz.org/Download_solaris.php)
* [Windows](http://www.graphviz.org/Download_windows.php)


Installation
============

To enable plantuml-clj for your project, create an alias for plantuml-clj (i.e: :plantuml), then put the following in the :extra-deps alias of your deps.edn file:

```edn
{:aliases {:plantuml {:extra-deps {plantuml-clj/plantuml-clj {:mvn/version "RELEASES"}}
                      :exec-fn plantuml-clj.core/plantuml
                      :exec-args {:input-dir "XXX"
                                  :fmt :format
                                  :output-dir "ZZZ"}}}}
```

then configure the keys `:input-dir`, `:fmt` and `:output-dir` of your `:exec-args` 

Configuration
=============

File formats:
- :png - Portable Network Graphics format
- :svg - Scalable Vector Graphics format
- :txt, :utxt - Text file format
- :eps, :eps:txt - Encapsulated PostScript format
- :pdf - Portable Document Format ; NOTE: broken

<!---
- :html, :html5 - HTML documents
- :mjpeg - MJPEG format
-->

Example configuration:

```edn
  {:aliases {:plantuml {:extra-deps {plantuml-clj/plantuml-clj {:mvn/version "1.0"}}
                        :exec-fn plantuml-clj.core/plantuml
                        :exec-args {:input-dir "resources/*.puml"
                                    :fmt :png
                                    :output-dir "target"}}}}
```


Usage
=====

To generate UML image files using configuration from deps.edn, you should use:

```bash
clj -X:plantuml :input-dir "doc/*.puml" :fmt :png :output-dir "target/doc"
```

Examples
========

Detailed example
----------------

To test the plugin, you can create a simple file and run plantuml-clj:
```
@startuml

User -> (Start)
User --> (Use the application) : A small label

:Main Admin: ---> (Use the application) : This is\nyet another\nlabel

@enduml
```
Output UML diagram should look like this:

![uml-example](http://plantuml.sourceforge.net/imgp/usecase_003.png)


Example project
---------------

Just clone current repository and try to play with [example project](https://github.com/jeremyvdw/plantuml-clj/tree/master/example) for better understanding how to use plantuml-clj.


Useful links
------------

More examples could be found here:
- [Sequence Diagram](http://plantuml.sourceforge.net/sequence.html)
- [Use Case Diagram](http://plantuml.sourceforge.net/usecase.html)
- [Class Diagram](http://plantuml.sourceforge.net/classes.html)
- [Activity Diagram](http://plantuml.sourceforge.net/activity.html) + [(more)](http://plantuml.sourceforge.net/activity2.html)
- [Component Diagram](http://plantuml.sourceforge.net/component.html)
- [State Diagram](http://plantuml.sourceforge.net/state.html)
- [Object Diagram](http://plantuml.sourceforge.net/objects.html)


Unit testing
============

To run unit tests:

```bash
clj -X:test
```

Adapted from
============

[lein-plantuml](https://github.com/vbauer/lein-plantuml) is an awsome Leiningen plugin for generating UML diagrams using PlantUML.

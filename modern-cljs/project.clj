(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2069"]
                 [compojure "1.1.6"]
                 [domina "1.0.3"]]

  ;; CLJ and CLJS source code path
  :source-paths ["src/clj" "src/cljs"]

  ;; lein-cljsbuild
  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.8"]]

  :cljsbuild {:builds
              [{;; CLJS source paths
                :source-paths ["src/cljs"]

                ;; Google Closure (CLS) options
                :compiler {;; js path
                           :output-to "resources/public/js/modern.js"

                           ;; minimal optimizations
                           :optimizations :whitespace

                           :pretty-print true}}]}

  :ring {:handler modern-cljs.core/handler})


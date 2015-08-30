(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2069"]
                 [compojure "1.1.6"]
                 [domina "1.0.3"]
                 [hiccups "0.2.0"]
                 [org.clojars.magomimmo/shoreleave-remote-ring "0.3.1-SNAPSHOT"]
                 [org.clojars.magomimmo/shoreleave-remote "0.3.1-SNAPSHOT"]
                 [com.cemerick/valip "0.3.2"]
                 [enlive "1.1.4"]]

  ;; CLJ and CLJS source code path
  :source-paths ["src/clj" "src/cljs" "src/brepl"]

  :test-paths ["target/test/clj" "target/test/cljs"]

  ;; lein-cljsbuild
  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.8"]
            [com.cemerick/clojurescript.test "0.2.1"]
            [com.keminglabs/cljx "0.3.0"]]

  :hooks [leiningen.cljsbuild]

  :cljx {:builds [{:source-paths ["test/cljx"] ;; cljx source dir
                   :output-path "target/test/clj" ;; clj output
                   :rules :clj} ;; clj generation rules

                  {:source-paths ["test/cljx"] ;; cljx source dir
                   :output-path "target/test/cljs" ;; cljs output
                   :rules :cljs}]} ;; cljs generation rules

  :cljsbuild {:crossovers [valip.core valip.predicates
                           modern-cljs.login.validators
                           modern-cljs.shopping.validators]
              :builds
              {:dev
               {;; CLJS source paths
                :source-paths ["src/cljs" "src/brepl"]

                ;; Google Closure (CLS) options
                :compiler {;; js path
                           :output-to "resources/public/js/modern_dbg.js"

                           ;; minimal optimizations
                           :optimizations :whitespace

                           :pretty-print true}}
               :pre-prod
               {;; CLJS source paths
                :source-paths ["src/cljs" "src/brepl"]

                ;; Google Closure (CLS) options
                :compiler {;; js path
                           :output-to "resources/public/js/modern_pre.js"

                           ;; minification
                           :optimizations :simple

                           :pretty-print false}}
               :prod
               {;; CLJS source paths
                :source-paths ["src/cljs"]

                ;; Google Closure (CLS) options
                :compiler {;; js path
                           :output-to "resources/public/js/modern.js"

                           ;; killing of unused code
                           :optimizations :advanced

                           :pretty-print false}}
               :ws-unit-tests
               {;; CLJS source code and unit test paths
                :source-paths ["src/brepl" "src/cljs" "target/test/cljs"]

                ;; Google Closure Compiler options
                :compiler {;; the name of emitted JS script file for unit testing
                           :output-to "test/js/testable_dbg.js"

                           ;; minimum optimization
                           :optimizations :whitespace
                           ;; prettyfying emitted JS
                           :pretty-print true}}

               :simple-unit-tests
               {;; same path as above
                :source-paths ["src/brepl" "src/cljs" "target/test/cljs"]

                :compiler {;; different JS output name for unit testing
                           :output-to "test/js/testable_pre.js"

                           ;; simple optimization
                           :optimizations :simple

                           ;; no need prettification
                           :pretty-print false}}

               :advanced-unit-tests
               {;; same path as above
                :source-paths ["src/cljs" "target/test/cljs"]

                :compiler {;; different JS output name for unit testing
                           :output-to "test/js/testable.js"

                           ;; advanced optimization
                           :optimizations :advanced

                           ;; no need prettification
                           :pretty-print false}}}

              :test-commands {"phantomjs-whitespace"
                              ["phantomjs" :runner "test/js/testable_dbg.js"]

                              "phantomjs-simple"
                              ["phantomjs" :runner "test/js/testable_pre.js"]

                              "phantomjs-advanced"
                              ["phantomjs" :runner "test/js/testable.js"]}}

  :ring {:handler modern-cljs.core/app})


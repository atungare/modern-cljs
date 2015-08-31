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

  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.8"]]

  :min-lein-version "2.2.0"

  :hooks [leiningen.cljsbuild]

  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["target/test/clj"]

  :ring {:handler modern-cljs.core/app}

  :cljsbuild {:crossovers [valip.core
                           valip.predicates
                           modern-cljs.login.validators
                           modern-cljs.shopping.validators]
              :builds
              {:prod
               {
                :source-paths ["src/cljs"]
                :compiler {
                           :output-to "resources/public/js/modern.js"
                           :optimizations :advanced
                           :pretty-print false}}}}

  :profiles {:dev {
                   :source-paths ["src/brepl"]
                   :test-paths ["target/test/cljs"]
                   :clean-targets ["out"]
                   :dependencies [[com.cemerick/piggieback "0.1.2"]]
                   :plugins [[com.cemerick/clojurescript.test "0.2.1"]
                             [com.keminglabs/cljx "0.3.0"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :injections [(require '[cljs.repl.browser :as brepl]
                                         '[cemerick.piggieback :as pb])
                                (defn browser-repl []
                                  (pb/cljs-repl :repl-env
                                                (brepl/repl-env :port 9000)))]
                   :aliases {"clean-test!" ["do" "clean," "cljx" "once," "compile," "test"]
                             "clean-start!" ["do" "clean," "cljx" "once," "compile," "ring" "server-headless"]}

                   :cljx {:builds [{:source-paths ["test/cljx"] ;; cljx source dir
                                    :output-path "target/test/clj" ;; clj output
                                    :rules :clj} ;; clj generation rules

                                   {:source-paths ["test/cljx"] ;; cljx source dir
                                    :output-path "target/test/cljs" ;; cljs output
                                    :rules :cljs}]} ;; cljs generation rules

                   :cljsbuild {:test-commands {"phantomjs-whitespace"
                                               ["phantomjs" :runner "test/js/testable_dbg.js"]

                                               "phantomjs-simple"
                                               ["phantomjs" :runner "test/js/testable_pre.js"]

                                               "phantomjs-advanced"
                                               ["phantomjs" :runner "test/js/testable.js"]}
                               :builds {:dev
                                        {
                                         :source-paths ["src/cljs" "src/brepl"]
                                         :compiler {
                                                    :output-to "resources/public/js/modern_dbg.js"
                                                    :optimizations :whitespace
                                                    :pretty-print true}}
                                        :pre-prod
                                        {
                                         :source-paths ["src/cljs" "src/brepl"]
                                         :compiler {
                                                    :output-to "resources/public/js/modern_pre.js"
                                                    :optimizations :simple
                                                    :pretty-print false}}
                                        :ws-unit-tests
                                        {
                                         :source-paths ["src/brepl" "src/cljs" "target/test/cljs"]
                                         :compiler {
                                                    :output-to "test/js/testable_dbg.js"
                                                    :optimizations :whitespace
                                                    :pretty-print true}}
                                        
                                        :simple-unit-tests
                                        {
                                         :source-paths ["src/brepl" "src/cljs" "target/test/cljs"]
                                         :compiler {
                                                    :output-to "test/js/testable_pre.js"
                                                    :optimizations :simple
                                                    :pretty-print false}}
                                        
                                        :advanced-unit-tests
                                        {
                                         :source-paths ["src/cljs" "target/test/cljs"]
                                         :compiler {
                                                    :output-to "test/js/testable.js"
                                                    :optimizations :advanced
                                                    :pretty-print false}}}}}})


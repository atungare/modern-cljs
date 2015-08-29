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
                 [com.cemerick/valip "0.3.2"]]

  ;; CLJ and CLJS source code path
  :source-paths ["src/clj" "src/cljs" "src/brepl"]

  ;; lein-cljsbuild
  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.8"]]

  :cljsbuild {:crossovers [valip.core valip.predicates modern-cljs.login.validators]
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

                           :pretty-print false}}}}

  :ring {:handler modern-cljs.remotes/app})


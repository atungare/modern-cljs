(ns modern-cljs.core
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

;; defroutes is a macro that defines a function that chains handler
;;   functions in sequence and returns first non-nil value
(defroutes app-routes
  ;; root
  (GET "/" [] "<p>Hello, World, from Compojure!</p>")
  ;; static assets
  (route/resources "/")
  (route/not-found "Page not found."))

;; site adds boilerplate middleware
(def handler
  (handler/site app-routes))

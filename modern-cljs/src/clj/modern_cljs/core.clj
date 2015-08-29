(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.handler :refer [site]]
            [compojure.route :refer [resources not-found]]
            [modern-cljs.login :refer [authenticate-user]]))

;; defroutes is a macro that defines a function that chains handler
;;   functions in sequence and returns first non-nil value
(defroutes app-routes
  ;; root
  (GET "/" [] "<p>Hello, World, from Compojure!</p>")
  (POST "/login" [email password] (authenticate-user email password))
  ;; static assets
  (resources "/")
  (not-found "Page not found."))

;; site adds boilerplate middleware
(def handler
  (site app-routes))

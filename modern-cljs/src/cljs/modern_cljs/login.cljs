(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]]
                   [shoreleave.remotes.macros :as shore-macros])
  (:require [domina :refer [by-class by-id value append! destroy! attr]]
            [domina.events :refer [listen! prevent-default]]
            [hiccups.runtime]
            [modern-cljs.login.validators :refer [user-credential-errors]]
            [shoreleave.remotes.http-rpc :refer [remote-callback]]))

(defn validate-email-domain [email]
  (remote-callback :email-domain-errors
                   [email]
                   #(if %
                      (do
                        (append! (by-id "loginForm")
                                 (html [:div.help.email "The email domain doesn't exist."]))
                        false)
                      true)))

(defn validate-email [email]
  (destroy! (by-class "email"))
  (if-let [errors (:email (user-credential-errors (value email) nil))]
    (do
      (append! (by-id "loginForm") (html [:div.help.email (first errors)]))
      false)
    (validate-email-domain (value email))))

(defn validate-password [password]
  (destroy! (by-class "password"))
  (if-let [errors (:password (user-credential-errors nil (value password)))]
    (do
      (append! (by-id "loginForm") (html [:div.help.password (first errors)]))
      false)
    true))

(defn validate-form [e]
  (let [email (by-id "email")
        password (by-id "password")]
    (if (or (empty? (value email)) (empty? (value password)))
      (do
        (destroy! (by-class "help"))
        (prevent-default e)
        (append! (by-id "loginForm") (html [:div.help "Please complete the form"])))
      (if (and (validate-email email) (validate-password password))
        true
        (prevent-default e)))))

(defn ^:export init []
  (if (and js/document
           (aget js/document "getElementById"))
    (let [email (by-id "email")
          password (by-id "password")]
      (listen! (by-id "submit") :click (fn [e] (validate-form e)))
      (listen! email :blur (fn [e] (validate-email email)))
      (listen! password :blur (fn [e] (validate-password password))))))

;; (set! (.-onload js/window) init)


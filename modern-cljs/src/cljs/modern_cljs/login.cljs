(ns modern-cljs.login
  (:require-macros [hiccups.core :refer [html]])
  (:require [domina :refer [by-class by-id value append! destroy!]]
            [domina.events :refer [listen! prevent-default]]
            [hiccups.runtime]))

(def ^:dynamic *email-re* #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(defn validate-email [email]
  (destroy! (by-class "email"))
  (if (not (re-matches *email-re* (value email)))
    (do
      (append! (by-id "loginForm") (html [:div.help.email "Wrong Email"]))
      false)
    true))

(def ^:dynamic *password-re* #"^(?=.*\d).{4,8}$")

(defn validate-password [password]
  (destroy! (by-class "password"))
  (if (not (re-matches *password-re* (value password)))
    (do
      (append! (by-id "loginForm") (html [:div.help.password "Wrong Password"]))
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


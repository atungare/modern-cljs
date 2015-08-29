(ns modern-cljs.login
  (:require [modern-cljs.login.validators :refer [user-credential-errors]]
            [modern-cljs.login.java.validators :refer [email-domain-errors]]))

(def ^:dynamic *re-email*
  #"^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$")

(def ^:dynamic *re-password*
  #"^(?=.*\d).{4,8}$")

(declare validate-email validate-password)

(defn authenticate-user [email password]
  (if (or (boolean (user-credential-errors email password))
          (boolean (email-domain-errors email)))
    (str "Please complete the form.")
    (str email " and " password " validated, still need authentication!")))


(defn validate-email [email]
  (if (re-matches *re-email* email)
    true))

(defn validate-password [password]
  (if (re-matches *re-password* password)
    true))


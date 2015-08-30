(ns modern-cljs.remotes
  (:require [shoreleave.middleware.rpc :refer [defremote]]
            [modern-cljs.login.java.validators :as v]
            [modern-cljs.utils :refer [parse-integer parse-double]]))

(defremote calculate [quantity price tax discount]
  (let [q (parse-integer quantity)
        p (parse-double price)
        t (parse-double tax)
        d (parse-double discount)]
    (-> (* q p)
        (* (+ 1 (/ t 100)))
        (- d))))

(defremote email-domain-errors [email]
  (v/email-domain-errors email))

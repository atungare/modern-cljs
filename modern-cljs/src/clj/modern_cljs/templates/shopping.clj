(ns modern-cljs.templates.shopping
  (:require [net.cgrand.enlive-html :refer [deftemplate content do-> add-class set-attr attr=]]
            [modern-cljs.remotes :refer [calculate]]
            [modern-cljs.shopping.validators :refer [validate-shopping-form]]))

(defmacro maybe-error [expr]
  `(if-let [x# ~expr]
     (do-> (add-class "error")
           (content x#))
     identity))

(deftemplate update-shopping-form "public/shopping.html"
  [quantity price tax discount errors]

  [[:label (attr= :for "quantity")]] (maybe-error (first (:quantity errors)))
  [[:label (attr= :for "price")]] (maybe-error (first (:price errors)))
  [[:label (attr= :for "tax")]] (maybe-error (first (:tax errors)))
  [[:label (attr= :for "discount")]] (maybe-error (first (:discount errors)))

  [:#quantity] (set-attr :value quantity)
  [:#price] (set-attr :value price)
  [:#tax] (set-attr :value tax)
  [:#discount] (set-attr :value discount)

  [:#total] (if errors
              (set-attr :value "0.0")
              (set-attr :value
                        (format "%.2f" (double (calculate quantity price tax discount))))))

(defn shopping [q p t d]
  (update-shopping-form q p t d (validate-shopping-form q p t d)))

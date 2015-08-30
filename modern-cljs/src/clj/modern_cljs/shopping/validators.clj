(ns modern-cljs.shopping.validators
  (:require [valip.core :refer [validate]]
            [valip.predicates :refer [present? integer-string? decimal-string? gt]]))

(defn validate-shopping-form [quantity price tax discount]
  (validate {:quantity quantity :price price :tax tax :discount discount}

            [:quantity present? "Quantity can't be empty."]
            [:price present? "Price can't be empty."]
            [:tax present? "Tax can't be empty."]
            [:discount present? "Discount can't be empty."]

            [:quantity integer-string? "Quantity must be an integer."]
            [:price decimal-string? "Price must be a number."]
            [:tax decimal-string? "Tax must be a number."]
            [:discount decimal-string? "Discount must be a number."]

            [:quantity (gt 0) "Quantity must be positive."]))



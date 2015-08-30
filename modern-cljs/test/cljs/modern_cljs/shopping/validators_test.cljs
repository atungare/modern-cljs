(ns modern-cljs.shopping.validators-test
  (:require-macros [cemerick.cljs.test :refer (deftest are testing)])
  (:require [cemerick.cljs.test :as t]
            [modern-cljs.shopping.validators :refer [validate-shopping-form]]))

(deftest validate-shopping-form-test
  (testing "shopping form validation"

    (testing "/ happy path"
      (are [expected actual] (= expected actual)
           nil (validate-shopping-form "1" "0" "0" "0")
           nil (validate-shopping-form "1" "0.0" "0.0" "0.0")
           nil (validate-shopping-form "100" "100.25" "8.25" "123.45")))
    
    (testing "/ no presence"
      (are [expected actual] (= expected actual)

           "Quantity can't be empty."
           (first (:quantity (validate-shopping-form "" "0" "0" "0")))

           "Price can't be empty."
           (first (:price (validate-shopping-form "1" "" "0" "0")))

           "Tax can't be empty."
           (first (:tax (validate-shopping-form "1" "0" "" "0")))

           "Discount can't be empty."
           (first (:discount (validate-shopping-form "1" "0" "0" "")))))

    (testing "/ value type"
      (are [expected actual] (= expected actual)

           "Quantity must be an integer."
           (first (:quantity (validate-shopping-form "foo" "0" "0" "0")))

           "Quantity must be an integer."
           (first (:quantity (validate-shopping-form "1.1" "0" "0" "0")))

           "Price must be a number."
           (first (:price (validate-shopping-form "1" "foo" "0" "0")))

           "Tax must be a number."
           (first (:tax (validate-shopping-form "1" "0" "foo" "0")))

           "Discount must be a number."
           (first (:discount (validate-shopping-form "1" "0" "0" "foo")))))

    (testing "/ Value Range"
      (are [expected actual] (= expected actual)

           "Quantity must be positive."
           (first (:quantity (validate-shopping-form "-1" "0" "0" "0")))))))

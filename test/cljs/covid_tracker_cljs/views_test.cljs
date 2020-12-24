(ns covid-tracker-cljs.views-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [covid-tracker-cljs.views :as views]
            [covid-tracker-cljs.test-utils :refer [hiccup-found?]]))

(deftest view-test
    (let [province-edn {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
          province (views/province province-edn)
          header (views/header)]
      (testing "Province name appears on list"
        (is (hiccup-found? {:element province :string "Uusimaa"}))
        (is (hiccup-found? {:element province :string "Lohja"})))
      (testing "Header appears"
        (is (hiccup-found? {:element header :string "Covid-tracker-cljs"})))))

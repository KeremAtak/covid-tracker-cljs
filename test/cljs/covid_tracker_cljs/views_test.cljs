(ns covid-tracker-cljs.views-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [covid-tracker-cljs.views :as views]
            [covid-tracker-cljs.test-utils :refer [hiccup-found?]]))

(deftest view-test
  (let [province-edn [:Uusimaa {:points [:s3 :p3 :n3 :m3 :l3 :i4 :h4 :g4 :e4 :d4 :c4 :b4 :a4 :z3]
                                :offset [55 230]
                                :scale 1
                                :statistics {:deaths 1
                                             :infections 12}
                                :municipalities {:Helsinki {:statistics {:deaths 1
                                                                         :infections 10}}}}]
        shape-displayed (views/display-shape province-edn)
        header (views/header)]
    (testing "Province name appears on list"
      (is (hiccup-found? {:element shape-displayed :string "Uusimaa"})))
    (testing "Kuolleet appears"
      (is (hiccup-found? {:element shape-displayed :string "Kuolleet"})))
    (testing "Death count appears"
      (is (hiccup-found? {:element shape-displayed :string 12})))
    (testing "Header appears"
      (is (hiccup-found? {:element header :string "Covid-tracker-cljs"})))))

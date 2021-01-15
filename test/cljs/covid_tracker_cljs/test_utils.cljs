(ns covid-tracker-cljs.test-utils
  (:require [cljs.test :refer-macros [deftest testing is]]
            [clojure.string :as string]
            [hiccup-find.core :refer [hiccup-text]]))

(defn hiccup-found?
  "Checks whether string is found from a hiccup element"
  [{:keys [element string]}]
  (string/includes? (hiccup-text element) string))

(deftest test-utils-test
  (let [element [:div
                 [:p "One"]
                 [:p "Two"]
                 [:a "Three"]]]
    (testing "Test utility works"
      (is (hiccup-found? {:element element :string "One"}))
      (is (not (hiccup-found? {:element element :string "Four"}))))))

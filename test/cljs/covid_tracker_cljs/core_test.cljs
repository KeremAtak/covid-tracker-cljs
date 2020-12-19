(ns covid-tracker-cljs.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [covid-tracker-cljs.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 2 2))))

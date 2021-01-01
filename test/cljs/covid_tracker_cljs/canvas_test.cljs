(ns covid-tracker-cljs.canvas-test
  #_(:require [cljs.test :refer-macros [deftest testing is]]
            [covid-tracker-cljs.canvas :refer [point->extrema points->extremum]]))

#_(deftest canvas-test
  (testing "point->extrema"
    (is (= 50 (point->extrema {:extrema 60
                               :maximal? false
                               :point 50})))
    (is (= 60 (point->extrema {:extrema 60
                               :maximal? false
                               :point 70})))
    (is (= 70 (point->extrema {:extrema 60
                               :maximal? true
                               :point 70})))
    (is (= 60 (point->extrema {:extrema 60
                               :maximal? true
                               :point 50}))))
  (testing "point->extrema"
    (is (= {:maxima-x 50 :maxima-y 60
            :minima-x 0 :minima-y 0}
           (points->extremum {:maxima-x 0 :maxima-y 0
                              :minima-x 10000 :minima-y 10000
                              :points [[20 60] [40 50] [50 30] [40 10] [10 0] [15 30] [0 50]]})))))

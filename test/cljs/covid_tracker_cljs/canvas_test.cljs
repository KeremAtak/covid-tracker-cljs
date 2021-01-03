(ns covid-tracker-cljs.canvas-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [covid-tracker-cljs.canvas :refer [point->extrema map-dots-and-points->boundaries]]))

(deftest canvas-test
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
  (testing "map-dots-and-points->boundaries"
    (is (= [{:x 5 :y 5} {:x 50 :y 60}]
           (map-dots-and-points->boundaries {:map-dots {:a [20 60] :b [40 50] :c [50 30] :d [40 10] :e [0 0]
                                                        :a1 [10 5] :b1 [15 30] :c1 [5 50] :d1 [20 70]}
                                             :maxima-x 0 :maxima-y 0
                                             :minima-x 10000 :minima-y 10000
                                             :points [:a :b :c :d :a1 :b1 :c1]})))))

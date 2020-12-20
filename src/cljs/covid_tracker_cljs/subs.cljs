(ns covid-tracker-cljs.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::name
 (fn [db]
   (:name db)))

(reg-sub
 ::Uusimaa
 (fn [db]
   (:Uusimaa db)))

(reg-sub
 ::Pohjois-Karjala
 (fn [db]
   (:Pohjois-Karjala db)))

(reg-sub
 ::Uusimaa1
 (fn [db]
   (:Uusimaa1 db)))

(reg-sub
 ::Uusimaa2
 (fn [db]
   (:Uusimaa2 db)))

(reg-sub
 ::Uusimaa3
 (fn [db]
   (:Uusimaa3 db)))

(reg-sub
 ::Uusimaa4
 (fn [db]
   (:Uusimaa4 db)))

(reg-sub
 ::Uusimaa5
 (fn [db]
   (:Uusimaa5 db)))

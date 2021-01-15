(ns covid-tracker-cljs.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::background-color
 (fn [db]
   (:background-color db)))

(reg-sub
 ::db
 (fn [db]
   db))

(reg-sub
 ::graphics
 (fn [db]
   (:graphics db)))

(reg-sub
 ::map-dots
 (fn [db]
   (:map-dots db)))

(reg-sub
 ::province-to-display
 (fn [db]
   (:province-to-display db)))

(reg-sub
 ::shapes
 (fn [db]
   (:shapes db)))

(reg-sub
 ::success-http-result
 (fn [db]
   (:success-http-result db)))

(reg-sub
 ::failure-http-result
 (fn [db]
   (:failure-http-result db)))

(reg-sub
 ::loading?
 (fn [db]
   (:loading? db)))

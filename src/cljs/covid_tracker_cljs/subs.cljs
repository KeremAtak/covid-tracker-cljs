(ns covid-tracker-cljs.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::background-color
 (fn [db]
   (:background-color db)))

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

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
 ::provinces
 (fn [db]
   (:provinces db)))

(reg-sub
 ::shapes
 (fn [db]
   (:shapes db)))

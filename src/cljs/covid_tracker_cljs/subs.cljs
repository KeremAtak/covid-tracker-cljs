(ns covid-tracker-cljs.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::provinces
 (fn [db]
   (:provinces db)))

(reg-sub
 ::background-color
 (fn [db]
   (:background-color db)))

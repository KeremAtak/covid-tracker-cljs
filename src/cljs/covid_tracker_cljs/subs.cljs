(ns covid-tracker-cljs.subs
  (:require
   [re-frame.core :refer [reg-sub]]))

(reg-sub
 ::provinces
 (fn [db]
   (:provinces db)))


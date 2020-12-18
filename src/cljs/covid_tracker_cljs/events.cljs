(ns covid-tracker-cljs.events
  (:require
   [re-frame.core :as re-frame]
   [covid-tracker-cljs.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

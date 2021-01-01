(ns covid-tracker-cljs.events
  (:require [covid-tracker-cljs.db :as db]
            [re-frame.core :refer [reg-event-db]]))

(reg-event-db
 ::change-background
 (fn [db [_ new-background-color]]
   (assoc db :background-color new-background-color)))

(reg-event-db
 ::init-db
 (fn [_ _]
   db/app-db))

(ns covid-tracker-cljs.events
  (:require [covid-tracker-cljs.db :as db]
            [covid-tracker-cljs.subs :as subs]
            [re-frame.core :refer [inject-cofx reg-cofx reg-event-db reg-event-fx subscribe]]))

(reg-event-db
 ::init-db
 (fn [_ _]
   db/app-db))

(reg-event-db
 ::change-background
 (fn [db [_ new-background-color]]
   (assoc db :background-color new-background-color)))

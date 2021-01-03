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

(reg-event-db
 ::set-boundaries
 (fn [db [_ kwd boundaries]]
   (assoc-in db [:shapes kwd :boundaries] boundaries)))

(reg-event-db
 ::set-graphics
 (fn [db [_ graphics]]
   (assoc db :graphics graphics)))

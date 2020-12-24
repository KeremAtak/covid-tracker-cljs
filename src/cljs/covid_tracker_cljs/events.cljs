(ns covid-tracker-cljs.events
  (:require [covid-tracker-cljs.db :as db]
            [covid-tracker-cljs.subs :as subs]
            [re-frame.core :refer [inject-cofx reg-cofx reg-event-db reg-event-fx subscribe]]))

(defn rotate-cube [degree]
  (if (= degree 0.99)
    0
    (+ degree 0.01)))

(reg-event-db
 ::init-db
 (fn [_ _]
   db/app-db))

(reg-event-fx
 ::cube-degree
 [(inject-cofx ::cube-rotation)]
 (fn [{:keys [cube-degree db]}]
   {:db (assoc db :cube-degree cube-degree)}))

(reg-cofx
 ::cube-rotation
 (fn [cofx _]
   (let [current-degree (subscribe [::subs/cube-degree])
         degree (rotate-cube @current-degree)]
     (assoc cofx :cube-degree degree))))

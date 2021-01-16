(ns covid-tracker-cljs.events
  (:require [ajax.core :as ajax]
            [covid-tracker-cljs.db :as db]
            [clojure.walk :refer [keywordize-keys]]
            [day8.re-frame.http-fx]
            [re-frame.core :refer [reg-event-db reg-event-fx]]))

;; this is redefined in compiler options when in dev mode
(goog-define BACKEND_URL "https://covid-tracker-clj.herokuapp.com/api/thl/infections/all")

(reg-event-db
 ::bad-response
 (fn [db [_ result]]
    ;; result is a map containing details of the failure
   (assoc db :failure-http-result result)))

(reg-event-fx
 ::get-backend-statistics
 (fn [{db :db} _]
   {:http-xhrio {:method          :get
                 :uri             BACKEND_URL
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format)
                 :on-success      [::process-response]
                 :on-failure      [::bad-response]}
    :db  (assoc db :loading? true)}))

(reg-event-db
 ::init-db
 (fn [_ _]
   db/app-db))

(defn deep-merge [a & maps]
  (if (map? a)
    (apply merge-with deep-merge a maps)
    (apply merge-with deep-merge maps)))

(reg-event-db
 ::process-response
 (fn [db [_ result]]
   (assoc (deep-merge db (keywordize-keys result)) :loading? false)))

(reg-event-db
 ::set-boundaries
 (fn [db [_ kwd boundaries]]
   (assoc-in db [:shapes kwd :boundaries] boundaries)))

(reg-event-db
 ::set-graphics
 (fn [db [_ graphics]]
   (assoc db :graphics graphics)))

(reg-event-db
 ::set-province-to-display
 (fn [db [_ province-to-display]]
   (assoc db :province-to-display province-to-display)))

(reg-event-db
 ::set-scale
 (fn [db [_ kwd scale]]
   (assoc-in db [:shapes kwd :scale] scale)))

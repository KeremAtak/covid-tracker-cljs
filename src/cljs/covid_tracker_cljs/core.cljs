(ns covid-tracker-cljs.core
  (:require [covid-tracker-cljs.config :as config]
            [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.views :as views]
            [reagent.dom :as rdom]
            [re-frame.core :refer [dispatch dispatch-sync clear-subscription-cache!]]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (clear-subscription-cache!)
  (let [root-el (.getElementById js/document "root")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/root] root-el)))

(defn init []
  (dispatch-sync [::events/init-db])
  (dispatch [::events/get-backend-statistics])
  (dev-setup)
  (mount-root))

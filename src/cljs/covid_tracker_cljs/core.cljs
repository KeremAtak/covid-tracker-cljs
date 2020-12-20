(ns covid-tracker-cljs.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [covid-tracker-cljs.events :as events]
   [covid-tracker-cljs.views :as views]
   [covid-tracker-cljs.config :as config]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/default-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/init-db])
  (dev-setup)
  (mount-root))

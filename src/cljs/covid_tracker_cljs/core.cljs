(ns covid-tracker-cljs.core
  (:require [covid-tracker-cljs.config :as config]
            [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.views :as views]
            [reagent.dom :as rdom]
            [re-frame.core :as re-frame]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "root")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/root] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/init-db])
  (dev-setup)
  (mount-root))

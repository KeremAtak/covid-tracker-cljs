(ns covid-tracker-cljs.core
  (:require [covid-tracker-cljs.config :as config]
            [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.views :as views]
            [reagent.dom :as rdom]
            [re-frame.core :as re-frame]
            [threeagent.core :as th]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [app-el (.getElementById js/document "app")
        root-el (.getElementById js/document "root")]
    (rdom/unmount-component-at-node app-el)
    (rdom/render [views/main-panel] app-el)
    (th/render [views/root] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/init-db])
  (js/setInterval (fn [] (re-frame/dispatch-sync [::events/cube-degree])) 10)
  (dev-setup)
  (mount-root))

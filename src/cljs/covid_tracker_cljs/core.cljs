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
        header-el (.getElementById js/document "header")
        root-el (.getElementById js/document "root")]
    (rdom/unmount-component-at-node app-el)
    (rdom/render [views/infographics] app-el)
    (rdom/unmount-component-at-node header-el)
    (rdom/render [views/header] header-el)
    (th/render [views/root] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/init-db])
  (js/setInterval (fn [] (re-frame/dispatch-sync [::events/cube-degree])) 10)
  (dev-setup)
  (mount-root))

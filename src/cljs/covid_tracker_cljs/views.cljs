(ns covid-tracker-cljs.views
  (:require [covid-tracker-cljs.subs :as subs]
            [re-frame.core :refer [subscribe]]
            [threeagent.core :as th]))

(defn root []
  (let [cube-degree (subscribe [::subs/cube-degree])]
    [:object {:position [0 -10 -2]}
     [:directional-light {:intensity 1
                          :position [0 50 100]
                          :cast-shadow true}]
     [:box {:position [0 8 -10]
            :height 3
            :width 3
            :depth 3
            :rotation [5 @cube-degree]
            :material {:color "red"
                       :opacity 0.5
                       :transparent false}}]]))

(defn province [prov]
  (let [province-name (:province prov)
        municipalities (:municipalities prov)]
    [:div
     {:style {:padding 10}}
     [:h3 province-name]
     [:ul
      (for [m municipalities]
        [:li m])]]))

(defn infographics []
  [:div
   {:style {:margin "auto" :width "20vw" :height "100vh"}}
   [:div {:style {:display "flex" :flex-wrap "wrap"}}
    (let [provinces @(subscribe [::subs/provinces])]
      (map province provinces))]])

(defn header []
  [:header
   {:style {:margin "auto" :width "50%" :height "10vh"}}
   [:h2 "Covid-tracker-cljs"]])

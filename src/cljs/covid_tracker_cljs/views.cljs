(ns covid-tracker-cljs.views
  (:require [covid-tracker-cljs.canvas :as canvas]
            [covid-tracker-cljs.subs :as subs]
            [re-frame.core :refer [subscribe]]))

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
   {:style {:margin "auto" :width "50%" :height "20vh"}}
   [:h2 "Covid-tracker-cljs - Click on Pohjois-Karjala to do things.."]])

(defn root []
  [:div
   (header)
   [:div {:style {:display "flex" :flex-wrap "wrap" :flex-direction "row"}}
    [:div#canvas
     (canvas/covid-canvas)]
    (infographics)]])

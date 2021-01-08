(ns covid-tracker-cljs.views
  (:require [covid-tracker-cljs.canvas :as canvas]
            [covid-tracker-cljs.subs :as subs]
            [re-frame.core :refer [subscribe]]))

(defn display-shape
  "Displays shapes which are either provinces or municipalities"
  [[shape-kwd shape-values]]
  (let [shape-name (name shape-kwd)
        statistics (:statistics shape-values)
        {:keys [deaths infections]} statistics]
    [:div {:style {:padding 10}}
     [:h3 shape-name]
     [:p "Sairastuneet: " infections]
     [:p "Kuolleet: " deaths]]))

(defn municipalities
  "Displays municiplaities of one province"
  [provinces]
  (let [province-to-display @(subscribe [::subs/province-to-display])]
    (if (not (nil? province-to-display))
      (let [province (province-to-display provinces)
            municipalities (:municipalities province)]
        [:div
         [:h3 (name province-to-display)]
         [:div {:style {:display "flex" :flex-wrap "wrap"}}
         (map display-shape municipalities)]])
      [:p "Siirrä hiiri maakunnan yläpuolelle!"])))

(defn infographics []
  [:div
   {:style {:margin 10 :width "50vw" :height "100vh"}}
   [:h2 "Maakuntien tilanne: "]
   (let [shapes @(subscribe [::subs/shapes])]
     [:div
      [:div
       (municipalities shapes)]
      [:div {:style {:display "flex" :flex-wrap "wrap"}}
       (map display-shape shapes)]])])

(defn header []
  [:header
   {:style {:margin "auto" :width "50%" :height "20vh"}}
   [:h2 "Covid-tracker-cljs - Drag over objects to do things.."]])

(defn root []
  [:div
   (header)
   [:div {:style {:display "flex" :flex-wrap "wrap" :flex-direction "row"}}
    [:div#canvas]
    (infographics)]])

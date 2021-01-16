(ns covid-tracker-cljs.views
  (:require [covid-tracker-cljs.canvas :as canvas]
            [covid-tracker-cljs.subs :as subs]
            [re-frame.core :refer [subscribe]]))

(defn display-shape
  "Displays shapes which are either provinces or municipalities"
  [[shape-kwd shape-values]]
  (let [shape-name (name shape-kwd)
        statistics (:statistics shape-values)
        infections (:infections statistics)]
    [:div {:style {:padding 10}}
     [:h3 shape-name]
     [:p "Sairastuneet: " infections]]))

(defn municipalities
  "Displays municipalities of one province"
  [provinces]
  (let [province-to-display @(subscribe [::subs/province-to-display])]
    (if (not (nil? province-to-display))
      (let [province (province-to-display provinces)
            infections (->> province :statistics :infections)
            municipalities (:municipalities province)]
        [:div
         [:h3 (name province-to-display)]
         [:p "Sairastuneet: " infections]
         [:div {:style {:display "flex" :flex-wrap "wrap"}}
         (map display-shape municipalities)]])
      [:p "Siirrä hiiri maakunnan yläpuolelle!"])))

(defn infographics []
  [:div
   {:style {:margin 10 :width "50vw" :height "100vh"}}
   (let [shapes @(subscribe [::subs/shapes])
         {:keys [deaths infections]} (->> shapes :Kaikki :statistics)]
     [:div
      [:h2 "Koronatilastot koko maassa"]
      [:p "Sairastuneet: " infections]
      [:p "Kuolleet: " deaths]
      [:h2 "Maakuntien tilanne: "]
      [:div {:style {:display "flex" :flex-wrap "wrap"}}
       [:div
        (municipalities shapes)]
       [:div
        [:h2 "Kaikki maakunnat"]]
       (map display-shape shapes)]])])

(defn header []
  [:header
   {:style {:margin "auto" :width "50%" :height "20vh"}}
   [:h2 "Covid-tracker-cljs - tuo hiiri maakunnan yli näyttääksesi lisää tilastoja."]
   (let [loading? @(subscribe [::subs/loading?])]
     (when loading?
        [:a {:href "https://github.com/KeremAtak/covid-tracker-cljs/"}
         "Eikö tilastot lataudu? Lue README:"]))])

(defn root []
  [:div
   (header)
   [:div {:style {:display "flex" :flex-wrap "wrap" :flex-direction "row"}}
    [:div#canvas]
    (infographics)]])

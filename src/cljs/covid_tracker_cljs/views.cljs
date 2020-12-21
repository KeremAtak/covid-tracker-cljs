(ns covid-tracker-cljs.views
  (:require
   [covid-tracker-cljs.subs :as subs]
   [re-frame.core :refer [subscribe]]))

;; This hardcoded stuff needs to be removed
(def province-keywords [::subs/Uusimaa ::subs/Pohjois-Karjala ::subs/Uusimaa1
                        ::subs/Uusimaa2 ::subs/Uusimaa3 ::subs/Uusimaa4 ::subs/Uusimaa5])

(defn province [prov]
  (let [province-name (:province prov)
        municipalities (:municipalities prov)]
    [:div
     {:style {:padding 10}}
     [:h3 province-name]
     [:ul
      (for [m municipalities]
        [:li m])]]))

(defn main-panel []
  [:div
   {:style {:margin "auto" :width "50vw" :height "100vh"}}
   [:div {:style {:display "flex" :flex-wrap "wrap"}}
    (let [provinces @(subscribe [::subs/provinces])]
      (map province provinces))]])

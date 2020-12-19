(ns covid-tracker-cljs.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :refer [box h-box v-box p]]
   [covid-tracker-cljs.subs :as subs]))

#_(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name)
     :level :level1]))

#_(defn main-panel []
  [re-com/v-box
   :height "100%"
   :children [[title]]])

#_(def provinces [1 2 3 4 5 6 7])

(def provinces [{:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Pohjois-Karjala" :municipalities ["Joensuu" "Liperi" "Rääkkylä" "Nurmes"
                                                              "Lieksa" "Outokumpu" "Polvijärvi" "Kitee"
                                                              "Kontiolahti"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
                {:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}])



#_(defn province [{:keys [:municipalities municipalities :province province]}]
  [:div
   {:style {:padding 10}}
   [:h3 "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red"]
    " text."]])


(defn province [{:keys [:municipalities municipalities :province province]}]
  [:div
   {:style {:padding 10}}
   [:h3 province]
   [:ul
    (for [m municipalities]
      [:li m])]])


(defn default-panel []
  [:div
   {:style {:margin "auto" :width "50vw" :height "100vh"}}
   [:div {:style {:display "flex" :flex-wrap "wrap"}}
    (map province provinces)]])

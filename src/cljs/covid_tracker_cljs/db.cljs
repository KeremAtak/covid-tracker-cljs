(ns covid-tracker-cljs.db)

(def app-db
  {:background-color 50
   :provinces
   [{:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Pohjois-Karjala" :municipalities ["Joensuu" "Liperi" "Rääkkylä" "Nurmes"
                                                  "Lieksa" "Outokumpu" "Polvijärvi" "Kitee"
                                                  "Kontiolahti"]}
    {:province "Uusimaa1" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa2" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa3" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa4" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa5" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}]
   :shapes {:cube {:points [[20 20] [20 40] [40 40] [40 20]]
                   :position [100 200]}
            :pohjois-karjala {:boundaries [{:x 20 :y 60}
                                           {:x 70 :y 120}]
                              :points [[20 60] [40 50] [50 30] [40 10] [10 0] [15 30] [0 50]]
                              :position [20 60]}}})

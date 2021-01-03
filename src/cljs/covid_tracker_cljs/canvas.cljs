(ns covid-tracker-cljs.canvas
  (:require [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.subs :as subs]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [re-frame.core :refer [dispatch subscribe]]))

(defn update-bg [state]
  (let [background-color @(subscribe [::subs/background-color])]
    (dispatch [::events/change-background (- background-color 5)])
    state))

(defn check-mouse! [state]
  (let [shapes @(subscribe [::subs/shapes])
        [minima maxima] (-> shapes :pohjois-karjala :boundaries)]
    (if (and (< (:x minima) (q/mouse-x) (:x maxima))
             (< (:y minima) (q/mouse-y) (:y maxima)))
      (update-bg state)
      state)))

(defn draw-image [graphic shape]
  (let [[minima _] (:boundaries shape)]
    (q/image graphic
             (:x minima)
             (:y minima))))

(defn draw [state]
  (let [background-color @(subscribe [::subs/background-color])
        graphics @(subscribe [::subs/graphics])
        shapes @(subscribe [::subs/shapes])]
    (q/background background-color)
    (if (nil? graphics)
      (q/text "Loading" 10 10)
      (doseq [[graphic shape] (map list graphics shapes)]
        (draw-image (second graphic) (second shape))))))

(defn point->extrema
  "Calculates whether value is more extreme than the other. Highest?-boolean determines
   if we're looking for minimal or maximal value."
  [{:keys [extrema maximal? point]}]
  (cond
    (and maximal? (< extrema point)) point
    (and (not maximal?) (< point extrema)) point
    :else extrema))

(defn map-dots-and-points->boundaries
  "Calculates the maxima values recursively"
  [{:keys [map-dots maxima-x maxima-y minima-x minima-y points]}]
  (if (empty? points)
    [{:x minima-x :y minima-y}
     {:x maxima-x :y maxima-y}]
    (let [point (first points)
          [x y] (point map-dots)
          new-maxima-x (point->extrema {:extrema maxima-x
                                        :maximal? true
                                        :point x})
          new-maxima-y (point->extrema {:extrema maxima-y
                                        :maximal? true
                                        :point y})
          new-minima-x (point->extrema {:extrema minima-x
                                        :maximal? false
                                        :point x})
          new-minima-y (point->extrema {:extrema minima-y
                                        :maximal? false
                                        :point y})
          rest-points (rest points)]
      (map-dots-and-points->boundaries {:map-dots map-dots
                                        :maxima-x new-maxima-x :maxima-y new-maxima-y
                                        :minima-x new-minima-x :minima-y new-minima-y
                                        :points rest-points}))))

(defn form-shape! [shape]
  (let [background-color @(subscribe [::subs/background-color])
        map-dots @(subscribe [::subs/map-dots])
        [shape-keyword shape-data] shape
        shape-points (:points shape-data)
        boundaries (map-dots-and-points->boundaries {:map-dots map-dots
                                                     :maxima-x 0 :maxima-y 0
                                                     :minima-x 10000 :minima-y 10000
                                                     :points shape-points})
        [minima maxima] boundaries
        graphics (q/create-graphics (- (:x maxima) (:x minima)) (- (:y maxima) (:y minima)))]
    (q/with-graphics graphics
      #_(q/background 240)
      (q/begin-shape)
    ;; delete scale here?
      (q/scale 1)
      (q/fill 255 0 0)
      (doseq [kwd shape-points]
        (let [[x y] (kwd map-dots)]
          (q/vertex (- x (:x minima)) (- y (:y minima)))))
      (q/end-shape))
    (dispatch [::events/set-boundaries shape-keyword boundaries])
    graphics))

(defn setup [state]
   (let [shapes @(subscribe [::subs/shapes])
         graphics (map form-shape! shapes)
         municipality-keywords (keys shapes)]
     (dispatch [::events/set-graphics (zipmap municipality-keywords graphics)])))


(q/defsketch covid-canvas
  :setup setup
  :host "canvas"
  :size [800 1200]
  :renderer :p2d
  :draw draw
  :mouse-clicked check-mouse!
  :middleware [m/fun-mode])

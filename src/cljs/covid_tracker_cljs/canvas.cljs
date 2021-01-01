(ns covid-tracker-cljs.canvas
  (:require [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.subs :as subs]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [re-frame.core :refer [dispatch subscribe]]))

(def pohjois-karjala-points [[20 60] [40 50] [50 30] [40 10] [10 0] [15 30] [0 50]])
(def pohjois-karjala-position [20 60])
(def cube-points [[20 20] [20 40] [40 40] [40 20]])
(def cube-position [100 200])

(defn point->extrema
  "Calculates whether value is more extreme than the other. Highest?-boolean determines
   if we're looking for minimal or maximal value."
  [{:keys [extrema maximal? point]}]
  (cond
    (and maximal? (< extrema point)) point
    (and (not maximal?) (< point extrema)) point
    :else extrema))

(defn points->extremum
  "Calculates the maxima values recursively"
  [{:keys [maxima-x maxima-y
           minima-x minima-y
           points]}]
  (if (empty? points)
    {:maxima-x maxima-x :maxima-y maxima-y
     :minima-x minima-x :minima-y minima-y}
    (let [[x y] (first points)
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
      (points->extremum {:maxima-x new-maxima-x :maxima-y new-maxima-y
                         :minima-x new-minima-x :minima-y new-minima-y
                         :points rest-points}))))

(defn position-and-points->boundaries [{:keys [position points]}]
  (let [extremum (points->extremum {:maxima-x 0 :maxima-y 0
                                    :minima-x 10000 :minima-y 10000
                                    :points points})
        maxima-x (:maxima-x extremum)
        maxima-y (:maxima-y extremum)
        minima-x (:minima-x extremum)
        minima-y (:minima-y extremum)
        x (first position)
        y (second position)]
        [{:x (+ x minima-x) :y (+ y minima-y)}
         {:x (+ x maxima-x) :y (+ y maxima-y)}]))

(defn form-shape [{:keys [color points scale-kwd state]}]
  (q/background @(subscribe [::subs/background-color]))
  (q/begin-shape)
  (q/scale (or (scale-kwd state) 1))
  (q/fill (first color) (second color) (last color))
  (doseq [[x y] points]
    (q/vertex x y))
  (q/end-shape))

(defn setup [state]
  ;; Maybe calculate boundaries in this let..
  (let [gr (q/create-graphics 50 60)
        gg (q/create-graphics 150 150)]
    (q/with-graphics gr
      (form-shape {:color [0 255 0]
                   :points pohjois-karjala-points
                   :scale-kwd :pohjois-karjala-scale
                   :state state}))
    (q/with-graphics gg
      (form-shape {:color [255 0 0]
                   :points cube-points
                   :scale-kwd :nothing
                   :state state}))
    (q/set-state! :pohjois-karjala gr
                  :cube gg
                  :cube-position cube-position
                  :pohjois-karjala-position pohjois-karjala-position
                  :pohjois-karjala-scale 1
                  :pohjois-karjala-boundaries (position-and-points->boundaries 
                                               {:points pohjois-karjala-points
                                                :position pohjois-karjala-position}))))

(defn update-bg [state]
  (let [background-color @(subscribe [::subs/background-color])]
    (dispatch [::events/change-background (- background-color 5)])
    state))

(defn check-mouse! [state]
  (let [[minima maxima] (:pohjois-karjala-boundaries state)]
    (if (and (< (:x minima) (q/mouse-x) (:x maxima))
             (< (:y minima) (q/mouse-y) (:y maxima)))
      (update-bg state)
      state)))

(defn draw [state]
  (q/background @(subscribe [::subs/background-color]))
  (if (or (nil? (:pohjois-karjala state))
          (nil? (:cube state)))
    (do
      (q/text "Loading" 10 10))
    (do
      (q/image (:pohjois-karjala state)
               (first (:pohjois-karjala-position state))
               (second (:pohjois-karjala-position state)))
      (q/image (:cube state)
               (first (:cube-position state))
               (second (:cube-position state))))))

(q/defsketch covid-canvas
  :setup setup
  :host "canvas"
  :size [500 500]
  :renderer :p2d
  :draw draw
  :mouse-clicked check-mouse!
  :middleware [m/fun-mode])

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

#_(defn point->extrema
  "Calculates whether value is more extreme than the other. Highest?-boolean determines
   if we're looking for minimal or maximal value."
  [{:keys [extrema maximal? point]}]
  (cond
    (and maximal? (< extrema point)) point
    (and (not maximal?) (< point extrema)) point
    :else extrema))

#_(defn points->extremum
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

#_(defn position-and-points->boundaries [{:keys [position points]}]
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

(defn form-shape [{:keys [color graphics shape state]}]
  (q/with-graphics graphics
    (q/background @(subscribe [::subs/background-color]))
    (q/begin-shape)
    ;; delete scale here?
    (q/scale 1)
    (q/fill (first color) (second color) (last color))
    (doseq [[x y] (:points shape)]
      (q/vertex x y))
    (q/end-shape)))

(defn setup [state]
  ;; Maybe calculate boundaries in this let..
  (let [pk (q/create-graphics 50 60)
        cu (q/create-graphics 120 140)
        shapes @(subscribe [::subs/shapes])
        pohjois-karjala (form-shape {:color [0 255 0]
                                     :graphics pk
                                     :shape (:pohjois-karjala shapes)
                                     :state state})
        cube (form-shape {:color [255 0 0]
                          :graphics cu
                          :shape (:cube shapes)
                          :state state})]
    (q/set-state! :pohjois-karjala pk
                  :cube cu)))

(defn update-bg [state]
  (let [background-color @(subscribe [::subs/background-color])]
    (dispatch [::events/change-background (- background-color 5)])
    state))

(defn check-mouse! [state]
  (println (q/mouse-x))
  (println (q/mouse-y))
  (let [shapes @(subscribe [::subs/shapes])
        [minima maxima] (-> shapes :pohjois-karjala :boundaries)]
    (if (and (< (:x minima) (q/mouse-x) (:x maxima))
             (< (:y minima) (q/mouse-y) (:y maxima)))
      (update-bg state)
      state)))

(defn draw [state]
  (let [background-color @(subscribe [::subs/background-color])
        shapes @(subscribe [::subs/shapes])]
    (q/background background-color)
  (if (or (nil? (:pohjois-karjala state))
          (nil? (:cube state)))
    (do
      (q/text "Loading" 10 10))
    (do
      (q/image (:pohjois-karjala state)
               (-> shapes :pohjois-karjala :position first)
               (-> shapes :pohjois-karjala :position second))
      (q/image (:cube state)
               (-> shapes :cube :position first)
               (-> shapes :cube :position second))))))

(q/defsketch covid-canvas
  :setup setup
  :host "canvas"
  :size [500 500]
  :renderer :p2d
  :draw draw
  :mouse-clicked check-mouse!
  :middleware [m/fun-mode])

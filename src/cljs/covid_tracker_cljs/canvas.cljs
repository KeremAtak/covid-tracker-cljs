(ns covid-tracker-cljs.canvas
  (:require [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.subs :as subs]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [re-frame.core :refer [dispatch subscribe]]))

(defn dispatch-if-different-value! [{:keys [scale-current scale-new shape-kwd]}]
  (when (not= scale-current scale-new)
    (dispatch [::events/set-scale shape-kwd scale-new])
    (dispatch [::events/set-province-to-display shape-kwd])))

(defn mouse-within-boundaries? [[minima maxima]]
  (and (< (:x minima) (q/mouse-x) (:x maxima))
       (< (:y minima) (q/mouse-y) (:y maxima))))

(defn check-mouse-position [_]
  (let [shapes @(subscribe [::subs/shapes])]
    (doseq [shape shapes]
      (let [[shape-kwd shape-content] shape
            scale (:scale shape-content)]
        (if (mouse-within-boundaries? (:boundaries shape-content))
          (dispatch-if-different-value! {:scale-current scale
                                         :scale-new 0.99
                                         :shape-kwd shape-kwd})
          (dispatch-if-different-value! {:scale-current scale
                                         :scale-new 1
                                         :shape-kwd shape-kwd}))))))

(defn draw-image! [{:keys [graphic shape]}]
  (let [graphic-data (second graphic)
        shape-data (second shape)
        boundaries (:boundaries shape-data)
        scale (:scale shape-data)
        [{:keys [x y]} _] boundaries]
    (q/scale scale)
    (q/image graphic-data x y)
    (q/scale (/ 1 scale))))

(defn draw [_]
  (let [background-color @(subscribe [::subs/background-color])
        graphics @(subscribe [::subs/graphics])
        shapes @(subscribe [::subs/shapes])]
    (q/background background-color)
    (if (nil? graphics)
      (q/text "Loading" 10 10)
      (doseq [[graphic shape] (map list graphics shapes)]
        (draw-image! {:graphic graphic
                      :shape shape})))))

(defn point->extrema
  "Calculates whether value is more extreme than the other. Highest?-boolean determines
   if we're looking for minimal or maximal value."
  [{:keys [extrema maximal? point]}]
  (cond
    (and maximal? (< extrema point)) point
    (and (not maximal?) (< point extrema)) point
    :else extrema))

(defn boundaries->boundaries-with-offsets
  "Adds the predefines offset so that the shapes will be spread more on the canvas."
  [{:keys [boundaries offset]}]
  (let [[minima maxima] boundaries
        [offset-x offset-y] offset]
    [{:x (+ offset-x (:x minima)) :y (+ offset-y (:y minima))}
     {:x (+ offset-x (:x maxima)) :y (+ offset-y (:y maxima))}]))

(defn map-dots-and-points->boundaries
  "Calculates the maxima values recursively. Returns the value with offsets."
  [{:keys [map-dots maxima-x maxima-y minima-x minima-y offset points]}]
  (if (empty? points)
    (boundaries->boundaries-with-offsets {:boundaries
                                          [{:x minima-x :y minima-y}
                                           {:x maxima-x :y maxima-y}]
                                          :offset offset})
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
                                        :offset offset
                                        :points rest-points}))))

(defn form-shape! [shape]
  (let [map-dots @(subscribe [::subs/map-dots])
        [shape-kwd shape-data] shape
        shape-points (:points shape-data)
        offset (:offset shape-data)
        boundaries (map-dots-and-points->boundaries {:map-dots map-dots
                                                     :maxima-x 0 :maxima-y 0
                                                     :minima-x 10000 :minima-y 10000
                                                     :points shape-points
                                                     :offset offset})
        [minima maxima] boundaries
        [offset-x offset-y] offset
        graphics (q/create-graphics (- (:x maxima) (:x minima))
                                    (- (:y maxima) (:y minima)))]
    (q/with-graphics graphics
      #_(q/background 240)
      (q/begin-shape)
      (q/fill 255 0 0)
      (doseq [kwd shape-points]
        (let [[x y] (kwd map-dots)]
          (q/vertex (+ offset-x (- x (:x minima))) (+ offset-y (- y (:y minima))))))
      (q/end-shape))
    (dispatch [::events/set-boundaries shape-kwd boundaries])
    graphics))

(defn setup [_]
   (let [shapes @(subscribe [::subs/shapes])
         graphics (map form-shape! shapes)
         municipality-keywords (keys shapes)]
     (dispatch [::events/set-graphics (zipmap municipality-keywords graphics)])))

(q/defsketch covid-canvas
  :setup setup
  :host "canvas"
  :size [600 900]
  :renderer :p2d
  :draw draw
  :update check-mouse-position
  :middleware [m/fun-mode])

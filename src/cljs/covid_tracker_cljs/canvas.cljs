(ns covid-tracker-cljs.canvas
  (:require [covid-tracker-cljs.events :as events]
            [covid-tracker-cljs.subs :as subs]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [re-frame.core :refer [dispatch subscribe]]))

(defn form-shape! [{:keys [color graphics shape]}]
  (q/with-graphics graphics
    (q/background @(subscribe [::subs/background-color]))
    (q/begin-shape)
    ;; delete scale here?
    (q/scale 1)
    (let [[r g b] color]
      (q/fill r g b))
    (doseq [[x y] (:points shape)]
      (q/vertex x y))
    (q/end-shape)))

(defn setup [state]
  ;; Maybe calculate boundaries in this let..
  (let [pk (q/create-graphics 50 60)
        cu (q/create-graphics 120 140)
        shapes @(subscribe [::subs/shapes])]
    (form-shape! {:color [0 255 0]
                  :graphics pk
                  :shape (:pohjois-karjala shapes)})
    (form-shape! {:color [255 0 0]
                  :graphics cu
                  :shape (:cube shapes)})
    (q/set-state! :pohjois-karjala pk
                  :cube cu)))

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

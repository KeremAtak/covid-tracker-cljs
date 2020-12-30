(ns covid-tracker-cljs.canvas
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def pohjois-karjala-points [[20 60] [40 50] [50 30] [40 10] [10 0] [15 30] [0 50]])

(defn form-shape [{:keys [background-color color points scale-kwd state]}]
  (q/background (or (:background-color state) background-color))
  (q/begin-shape)
  (q/scale (or (scale-kwd state) 1))
  (q/fill (first color) (second color) (last color))
  (doseq [[x y] points]
    (q/vertex x y))
  (q/end-shape))

(defn setup [state]
  (let [gr (q/create-graphics 50 60)
        gg (q/create-graphics 150 150)]
    (q/with-graphics gr
      (form-shape {:background-color 50
                   :color [0 255 0]
                   :points pohjois-karjala-points
                   :scale-kwd :pohjois-karjala-scale
                   :state state}))
    (q/with-graphics gg
      (form-shape {:background-color 0
                   :color [255 0 0]
                   :points [[20 20] [20 40] [40 40] [40 20]]
                   :scale-kwd :nothing
                   :state state}))
    (q/set-state! :pohjois-karjala gr
                  :cube gg
                  :background-color 50
                  :pohjois-karjala-scale 1
                  :pohjois-karjala-boundaries [{:x 0 :y 0} {:x 40 :y 60}])))

(defn update-bg [state]
  (update-in state [:background-color] #(+ % 5)))

(defn check-mouse! [state]
  (let [coords (:pohjois-karjala-boundaries state)]
    (println (:x (first coords)))
    (println (:x (second coords)))
    (println (:y (first coords)))
    (println (:y (second coords)))
    (println (q/mouse-x))
    (println (q/mouse-y))
    (if (and (< (:x (first coords)) (q/mouse-x) (:x (second coords)))
             (< (:y (first coords)) (q/mouse-y) (:y (second coords))))
      (update-in state [:background-color] #(+ % 5)))))

(defn draw [state]
  (q/background (:background-color state))
  (if (or (nil? (:pohjois-karjala state))
          (nil? (:cube state)))
    (do
      (q/text "Loading" 10 10)
      (println (:pohjois-karjala state)))
    (do
      (q/image (:pohjois-karjala state) 0 0)
      (q/image (:cube state) 100 200))))

(q/defsketch covid-canvas
  :setup setup
  :host "canvas"
  :size [500 500]
  :renderer :p2d
  :draw draw
  :mouse-clicked check-mouse!
  :middleware [m/fun-mode])

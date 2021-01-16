(ns covid-tracker-cljs.server
  (:require [config.core :refer [env]]
            [covid-tracker-cljs.handler :refer [handler]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

 (defn -main [& _args]
   (let [port (or (env :port) 3000)]
     (run-jetty handler {:port port :join? false})))

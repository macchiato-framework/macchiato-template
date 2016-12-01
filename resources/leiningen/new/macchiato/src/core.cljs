(ns {{project-ns}}.core
  (:require
    [{{project-ns}}.routes :refer [router]]
    [macchiato.env :as config]
    [macchiato.http :refer [handler]]
    [macchiato.session.middleware :refer [wrap-session]]
    [mount.core :as mount :refer [defstate]]
    [taoensso.timbre :refer-macros [log trace debug info warn error fatal]]))

(defstate env :start (config/env))
(defstate http :start (js/require "http"))

(defn app []
  (mount/start)
  (let [host (or (:host env) "127.0.0.1")
        port (or (:port env) 3000)]
    (-> @http
        (.createServer
          (-> router
              (wrap-session)
              (handler {:cookies {:signed? false}})))
        (.listen port host #(info "{{name}} started on" host ":" port)))))

(defn start-workers [os cluster]
  (dotimes [_ (-> os .cpus .-length)]
    (.fork cluster))
  (.on cluster "exit"
       (fn [worker code signal]
         (info "worker terminated" (-> worker .-process .-pid)))))

(defn main [& args]
  (let [os      (js/require "os")
        cluster (js/require "cluster")]
    (if (.-isMaster cluster)
      (start-workers os cluster)
      (app))))

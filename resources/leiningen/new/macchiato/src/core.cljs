(ns {{project-ns}}.core
  (:require
    [{{project-ns}}.routes :refer [router]]
    [macchiato.http :refer [handler]]
    [macchiato.session.middleware :refer [wrap-session]]
    [mount.core :as mount :refer [defstate]]
    [taoensso.timbre :refer-macros [log trace debug info warn error fatal]]))

(defstate http :start (js/require "http"))

(defn app []
  (let [host (or (.-HOST (.-env js/process)) "127.0.0.1")
        port (or (.-PORT (.-env js/process)) 3000)]
    (mount/start)
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

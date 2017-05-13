(ns {{project-ns}}.core
  (:require
    [{{project-ns}}.config :refer [env]]
    [{{project-ns}}.middleware :refer [wrap-defaults]]
    [{{project-ns}}.routes :refer [router]]
    [macchiato.server :as http]
    [macchiato.middleware.session.memory :as mem]
    [mount.core :as mount :refer [defstate]]
    [taoensso.timbre :refer-macros [log trace debug info warn error fatal]]))

(defn app []
  (mount/start)
  (let [host (or (:host @env) "127.0.0.1")
        port (or (some-> @env :port js/parseInt) 3000)]
    (http/start
      {:handler    (wrap-defaults router)
       :host       host
       :port       port
       :on-success #(info "{{name}} started on" host ":" port)})))

(defn main [& args] (app))

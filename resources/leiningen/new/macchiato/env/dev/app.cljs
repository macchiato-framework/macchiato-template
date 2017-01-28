 (ns ^:figwheel-always {{project-ns}}.app
  (:require
    [{{project-ns}}.core :as core]
    [cljs.nodejs :as node]
    [mount.core :as mount]))

;;workaround for Figwheel file reloading
(def process (node/require "process"))

(set! js/__dirname (str (-> process .-env .-PWD) "/target/out/./."))

(mount/in-cljc-mode)

(cljs.nodejs/enable-util-print!)

(.on js/process "uncaughtException" #(js/console.error %))

(set! *main-cli-fn* core/app)

 (ns {{project-ns}}.app
  (:require
    [{{project-ns}}.core :as core]
    [cljs.nodejs]
    [mount.core :as mount]))

(mount/in-cljc-mode)

(cljs.nodejs/enable-util-print!)

(set! *main-cli-fn* core/main)

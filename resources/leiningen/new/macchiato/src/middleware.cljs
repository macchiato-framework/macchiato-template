(ns {{project-ns}}.middleware
  (:require
    [macchiato.defaults :as defaults]))

(defn wrap-defaults [handler]
  (defaults/wrap-defaults handler defaults/site-defaults))



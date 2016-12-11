(ns {{project-ns}}.middleware
  (:require
    [macchiato.middleware.content-type :refer [wrap-content-type]]
    [macchiato.middleware.params :refer [wrap-params]]
    [macchiato.middleware.keywor-params :refer [wrap-keyword-params]]
    [macchiato.middleware.file :refer [wrap-file]]))

(defn wrap-defaults [handler]
      (-> handler
          wrap-content-type
          wrap-file))



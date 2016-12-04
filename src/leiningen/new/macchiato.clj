(ns leiningen.new.macchiato
  (:require [leiningen.new.templates
             :refer [renderer name-to-path ->files
                     sanitize sanitize-ns project-name]]
            [leiningen.core.main :as main]
            [clojure.string :refer [join]]))

(def render (renderer "macchiato"))

(defn template-data [name]
  {:full-name name
   :name (project-name name)
   :project-ns (sanitize-ns name)
   :sanitized (name-to-path name)})

(defn format-files-args [name]
  (let [data (template-data name)]
    [data
     ["LICENSE" (render "LICENSE" data)]
     ["README.md" (render "README.md" data)]
     [".gitignore" (render "gitignore" data)]
     ["project.clj" (render "project.clj" data)]
     ;; sources
     ["env/dev/user.clj" (render "env/dev/user.clj" data)]
     ["env/dev/{{sanitized}}/app.cljs" (render "env/dev/app.cljs" data)]
     ["env/prod/{{sanitized}}/app.cljs" (render "env/prod/app.cljs" data)]
     ["src/{{sanitized}}/core.cljs" (render "src/core.cljs" data)]
     ["src/{{sanitized}}/middleware.cljs" (render "src/middleware.cljs" data)]
     ["src/{{sanitized}}/routes.cljs" (render "src/routes.cljs" data)]
     ;; static assets
     ["static/index.html" (render "static/index.html" data)]
     ["static/css/site.css" (render "static/css/site.css" data)]
     ;; Heroku support
     ["system.properties" (render "system.properties" data)]
     ["Procfile" (render "Procfile" data)]]))

(defn instructions [name]
  (join
    "\n"
    ["\nQuick Start"
     "-----------\n"
     "run  the compiler:"
     (str "  cd " name)
     "  lein build\n"
     "run node in another terminal:"
     (str "  cd " name)
     (str "  node target/out/" name ".js\n")
     "see README.md for further instructions"]))

(defn macchiato [name]
  (main/info "Generating fresh 'lein new' Macchiato project.")
  (apply ->files (format-files-args name))
  (-> name instructions println))

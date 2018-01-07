(ns leiningen.new.macchiato
  (:require [leiningen.new.templates
             :refer [renderer name-to-path ->files
                     sanitize sanitize-ns project-name]]
            [leiningen.core.main :as main]
            [clojure.string :refer [join]]))

(def render (renderer "macchiato"))

(defn template-data [name options]
  (merge {:full-name name
          :name (project-name name)
          :project-ns (sanitize-ns name)
          :sanitized (name-to-path name)}
         options))

(defn format-files-args [name options]
  (let [data (template-data name options)]
    (concat [data
             ["LICENSE" (render "LICENSE" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             [".dockerignore" (render "dockerignore" data)]
             ["Dockerfile" (render "Dockerfile" data)]
             ["project.clj" (render "project.clj" data)]
             ;; sources
             ["env/dev/user.clj" (render "env/dev/user.clj" data)]
             ["env/dev/{{sanitized}}/app.cljs" (render "env/dev/app.cljs" data)]
             ["env/prod/{{sanitized}}/app.cljs" (render "env/prod/app.cljs" data)]
             ["src/server/{{sanitized}}/config.cljs" (render "src/server/config.cljs" data)]
             ["src/server/{{sanitized}}/core.cljs" (render "src/server/core.cljs" data)]
             ["src/server/{{sanitized}}/middleware.cljs" (render "src/server/middleware.cljs" data)]
             ["src/server/{{sanitized}}/routes.cljs" (render "src/server/routes.cljs" data)]
             ;;tests
             ["env/test/{{sanitized}}/app.cljs" (render "env/test/app.cljs" data)]
             ["test/{{sanitized}}/core_test.cljs" (render "test/core_test.cljs" data)]
             ;; static assets
             ["public/css/site.css" (render "public/css/site.css" data)]
             ;; Heroku support
             ["system.properties" (render "system.properties" data)]
             ["Procfile" (render "Procfile" data)]]
            (when (:browser options)
              [["src/browser/{{sanitized}}/app.cljs" (render "src/browser/app.cljs" data)]]))))

(defn instructions [name]
  (join
    "\n"
    ["\nQuick Start"
     "-----------\n"
     "run the compiler:"
     (str "  cd " name)
     "  lein build\n"
     "run Node.js in another terminal:"
     (str "  cd " name)
     "  npm start\n"
     "see README.md for further instructions"]))

(def available-options
  #{"+browser"})

(defn check-options [options]
  (let [options-set (into #{} options)]
    (when-not (clojure.set/subset? options-set available-options)
      (main/abort "\nError: invalid profile(s)\n"))))

(defn parse-options [options]
  (check-options options)
  (reduce (fn [res opt]
            (assoc res (keyword (subs opt 1)) true))
          {}
          options))

(defn macchiato [name & options]
  (main/info "Generating fresh 'lein new' Macchiato project.")
  (apply ->files (format-files-args name (parse-options options)))
  (-> name instructions println))

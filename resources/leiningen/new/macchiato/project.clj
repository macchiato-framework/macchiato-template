(defproject {{full-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[bidi "2.1.3"]
                 [com.cemerick/piggieback "0.2.2"]
                 [com.taoensso/timbre "4.10.0"]
                 [macchiato/hiccups "0.4.1"]
                 [macchiato/core "0.2.12"]
                 [macchiato/env "0.0.6"]
                 [mount "0.1.12"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 ;; needed for JDK 9 compatibility
                 [javax.xml.bind/jaxb-api "2.3.0"]]
  :min-lein-version "2.0.0"
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-doo "0.1.7"]
            [macchiato/lein-npm "0.6.4"]
            [lein-figwheel "0.5.16"]
            [lein-cljsbuild "1.1.5"]]
  :npm {:dependencies [[source-map-support "0.4.6"]]
        :write-package-json true}
  :source-paths ["src/server"{{#browser}} "src/browser"{{/browser}} "target/classes"]
  :target-path "target"
  :profiles
  {:server {:clean-targets ["target"]}
{{#browser}}
   :browser {:clean-targets ["public/js/compiled"]}
   :browser-dev
   [:browser
    {:cljsbuild
     {:builds {:dev
               {:source-paths ["src/browser"]
                :figwheel true
                :compiler {:main {{project-ns}}.app
                           :asset-path "js/compiled/dev/out"
                           :output-to "public/js/compiled/app.js"
                           :output-dir "public/js/compiled/dev/out"
                           :source-map-timestamp true}}}}
     :figwheel {:http-server-root "public"
                :nrepl-port 7001
                :reload-clj-files {:clj true :cljc true}
                :server-port 3450
                :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}]
{{/browser}}
   :dev
   [:server
    {:npm {:package {:main "target/out/{{name}}.js"
                     :scripts {:start "node target/out/{{name}}.js"}}}
     :dependencies [[figwheel-sidecar "0.5.16"]]
     :cljsbuild
     {:builds {:dev
               {:source-paths ["env/dev" "src/server"]
                :figwheel     true
                :compiler     {:main          {{project-ns}}.app
                               :output-to     "target/out/{{name}}.js"
                               :output-dir    "target/out"
                               :target        :nodejs
                               :optimizations :none
                               :pretty-print  true
                               :source-map    true
                               :source-map-timestamp false}}}}
     :figwheel
     {:http-server-root "public"
      :nrepl-port 7000
      :reload-clj-files {:clj true :cljc true}
      :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
     :source-paths ["env/dev"]
     :repl-options {:init-ns user}}]
   :test
   {:cljsbuild
    {:builds
     {:test
      {:source-paths ["env/test" "src/server" "test"]
       :compiler     {:main {{project-ns}}.app
                      :output-to     "target/test/{{name}}.js"
                      :target        :nodejs
                      :optimizations :none
                      :pretty-print  true
                      :source-map    true}}}}
    :doo {:build "test"}
    :dependencies [[pjstadig/humane-test-output "0.8.3"]]}
   :release
   {:npm {:package {:main "target/release/{{name}}.js"
                    :scripts {:start "node target/release/{{name}}.js"}}}
    :cljsbuild
    {:builds
     {:release
      {:source-paths ["env/prod" "src/server"]
       :compiler     {:main          {{project-ns}}.app
                      :output-to     "target/release/{{name}}.js"
                      :language-in   :ecmascript5
                      :target        :nodejs
                      :optimizations :simple
                      :pretty-print  false}}
{{#browser}}
      :release-browser
      {:source-paths ["src/browser"]
       :compiler {:main {{project-ns}}.app
                  :asset-path "js/compiled/out"
                  :output-to "public/js/compiled/app.js"
                  :output-dir "public/js/compiled/out"
                  :optimizations :advanced
                  :pretty-print false
                  :source-map "public/js/compiled/app.js.map"}}
{{/browser}}
      }}}}
  :aliases
  {"build" ["do"
            ["clean"]
            ["npm" "install"]
            ["figwheel" "dev"]]
{{#browser}}
   "build-browser" ["do"
                    ["with-profile" "browser-dev" "figwheel" "dev"]]
{{/browser}}
   "package" ["do"
              ["clean"]
              ["npm" "install"]
              ["with-profile" "release" "npm" "init" "-y"]
              ["with-profile" "release" "cljsbuild" "once" "release"]
{{#browser}}
              ["with-profile" "release" "cljsbuild" "once" "release-browser"]
{{/browser}}]
   "test" ["do"
           ["npm" "install"]
           ["with-profile" "test" "doo" "node"]]})

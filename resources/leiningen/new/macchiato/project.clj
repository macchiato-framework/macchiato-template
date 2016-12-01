(defproject {{full-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[bidi "2.0.14"]
                 [hiccups "0.3.0"]
                 [macchiato/http "0.0.7"]
                 [macchiato/session "0.0.2"]
                 [macchiato/response "0.0.1"]
                 [mount "0.1.10"]
                 [com.cemerick/piggieback "0.2.1"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/data.json "0.2.6" :classifier "aot"]
                 [com.taoensso/timbre "4.7.4"]]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-npm "0.6.2"]
            [lein-figwheel "0.5.8"]
            [lein-cljsbuild "1.1.4"]
  [org.clojure/clojurescript "1.9.293"]]
  :npm {:dependencies [[source-map-support "0.4.6"]
                       [ws "1.1.1"]]}
  :source-paths ["src" "target/classes"]
  :clean-targets ["target"]
  :target-path "target"
  :profiles
  {:dev
   {:cljsbuild
    {:builds {:dev
              {:source-paths ["src" "env/dev"]
               :figwheel     true
               :compiler     {:main          {{project-ns}}.app
                              :output-to     "target/out/{{name}}.js"
                              :output-dir    "target/out"
                              :target        :nodejs
                              :optimizations :none
                              :pretty-print  true
                              :source-map    true}}}}
    :figwheel
    {:http-server-root "public"
     :nrepl-port 7000
     :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

    :source-paths ["env/dev"]
    :repl-options {:init-ns user}}
   :release
   {:cljsbuild
    {:builds
     {:release
      {:source-paths ["src" "env/prod"]
       :compiler     {:main          {{project-ns}}.app
                      :output-to     "target/release/{{name}}.js"
                      :target        :nodejs
                      :optimizations :simple
                      :pretty-print  false}}}}}}
  :aliases
  {"build" ["do"
            ["clean"]
            ["npm" "install"]
            ["figwheel" "dev"]]
   "package" ["do"
              ["clean"]
              ["npm" "install"]
              ["npm" "init" "-y"]
              ["with-profile" "release" "cljsbuild" "once" "release"]]})

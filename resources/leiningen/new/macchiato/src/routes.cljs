(ns {{project-ns}}.routes
  (:require
    [bidi.bidi :as bidi]
    [hiccups.runtime])
  (:require-macros
    [hiccups.core :refer [html]]))

(defn home [req res]
  (res
    {:headers {}
     :status  200
     :body    (html
                [:html
                 [:body
                  [:h2 "Hello World!"]
                  [:p
                   "Your user-agent is: "
                   (str (-> req :headers :user-agent))]]])}))

(defn not-found [req res]
  (res
    {:headers {}
     :status  404
     :body    (html
                [:html
                 [:body
                  [:h2 (:uri req) " was not found"]]])}))

(def routes
  ["/"
   [["" home]
    [true not-found]]])

(defn router [req res]
  (let [route (->> req :uri (bidi/match-route routes) :handler)]
    (route req res)))

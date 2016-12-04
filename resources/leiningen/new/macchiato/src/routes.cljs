(ns {{project-ns}}.routes
  (:require
    [bidi.bidi :as bidi]
    [hiccups.runtime]
    [macchiato.util.response :as r])
  (:require-macros
    [hiccups.core :refer [html]]))

(defn home [req res raise]
  (-> (html
        [:html
         [:body
          [:h2 "Hello World!"]
          [:p
           "Your user-agent is: "
           (str (-> req :headers :user-agent))]]])
      (r/ok)
      (r/content-type "text/html")
      (res)))

(defn not-found [req res raise]
  (-> (html
        [:html
         [:body
          [:h2 (:uri req) " was not found"]]])
      (r/not-found)
      (r/content-type "text/html")
      (res)))

(def routes
  ["/"
   [["" home]
    [true not-found]]])

(defn router [req res raise]
  (let [route (->> req :uri (bidi/match-route routes) :handler)]
    (route req res raise)))

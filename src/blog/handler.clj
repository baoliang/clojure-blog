(ns blog.handler
  (:use blog.routes.home
        compojure.core)
  (:require [noir.util.middleware :as middleware] 
            [noir.session :as session]
            [noir.response :as res]
            [blog.models.schema :as schema]
            [compojure.route :as route]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (println "blog started successfully..."))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (println "shutting down..."))

;;append your application routes to the all-routes vector
(def all-routes [home-routes app-routes])
(def admin-uri-l ["/admin"
                  "/post-blog"
                  "/blog/delete"
                  "/blog/detail"
                  ])
(defn admin-auth [handler]
  (fn [request]
      (if (and (contains? admin-uri-l (request :uri))
               (= nil (session/get :user)))
        (res/redirect "/admin-blog")
        (handler request))))


(def app (-> all-routes
             middleware/app-handler
             admin-auth
             ;;add your middlewares here
             ))

(def war-handler (middleware/war-handler app))

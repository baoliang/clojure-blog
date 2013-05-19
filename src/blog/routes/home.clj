(ns blog.routes.home
  (:use 
    compojure.core
    korma.core)
  (:require [blog.views.layout :as layout]
            [blog.models.db  :as db]
            [clj-time.core :as time]
            [noir.session :as session]
            [markdown.core :as md]
            [noir.response :as response]
            [blog.util :as util]))
(defn sql-now []
  (java.sql.Timestamp. (.getTime (java.util.Date.))))

(defn home-page []
  (layout/render "home.html" {:content-l (select db/content)}))


(defn post-blog [id title tag content md]
  (if (= id "")
    (insert db/content 
      (values [{:title title 
                :tag tag 
                :md md
                :content content
                :created-at (sql-now)}]))
    (update db/content
      (set-fields {:title title 
                   :tag tag 
                   :md md
                   :content content
                   :updated-at (sql-now)})
      (where {:id (read-string id)})))
  (response/redirect "/admin"))
  
(defn get-blog-by-id [id]
  (let [blog (first (select db/content 
                                    (where {:id (read-string id)})))]
    (layout/render  "detail.html"
      {:content  (:content blog)
       :title (:title blog)})))


(defn get-blog []
  (select db/content))

(defn admin-login-auth [email pass]
  (let  [user 
        (select db/users)
          auth? (select db/users
                  (where {:email email 
                          :pass pass}))
         contents (select db/content)]
         (if-not (= 0 (.size auth?))
            (do 
              (session/put! :user email)
              (layout/render "admin/home.html"  {:contents contents}))
          
            (if (= (.size user) 0)
                (response/redirect "/set-init-user")
                (layout/render  "admin/login.html" 
                          {:error "Password is wrong!"})))))


(defn edit-blog [id]
  (layout/render "post-blog.html"
                 (first (select db/content 
                          (where {:id (read-string id)})))))

(defn delete-blog [id]
  (delete db/content 
    (where {:id (read-string id)}))
  (response/redirect "/admin"))

(defn set-init-user []
  (if (= 0 (.size (select db/users)))
    (layout/render "admin/set-init-user.html")
    (response/redirect "/admin-blog")))

(defn set-init-user-post [email pass]
  (insert db/users (values {:email email :pass pass}))
  (session/put! :user email)
  (response/redirect "/admin"))
(defn admin-quit []
  (session/remove! :user)
  (response/redirect "/admin-blog"))  

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/resume" [] (layout/render "resume.html"))
  (GET "/admin" [] (layout/render "admin/home.html"  {:contents (select db/content)}))
  (GET "/set-init-user" [] (set-init-user))
  (POST "/set-init-user" [email pass] (set-init-user-post email pass))
  (GET "/blog/edit" [id] (edit-blog id))
  (GET "/blog/delete" [id] (delete-blog id))
  (GET "/admin-blog" [] (layout/render "admin/login.html"))
  (GET "/post-blog" [](layout/render "post-blog.html"))
  (POST "/post-blog" [id title tag content md] (post-blog id title tag content md))
  (POST "/admin-login-auth" [email pass] (admin-login-auth email pass))
  (GET "/detail" [id] (get-blog-by-id id))
  (GET "/admin-quit" [] (admin-quit)))

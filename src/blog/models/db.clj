(ns blog.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [blog.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity users)
(defentity content)
(defentity kv)

(defn get-user [id]
  (first  (select 
    users
    (where {:id id})
    (limit 1))))



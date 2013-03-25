(ns blog.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [blog.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity users)

(defn create-user [user]
  (insert users
          (values user)))

(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

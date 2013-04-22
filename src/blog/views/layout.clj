(ns blog.views.layout
  (:use 
    noir.request
    fleet)
  (:require [clabango.parser :as parser]))

(def template-path "blog/views/templates/")

(defn fleet-compile [name]
  (fleet [params]
         (parser/render-file (str template-path name) {})))

(defn render 
  ([name params]
    (str ((fleet-compile name) params)))
  ([name]
    (str ((fleet-compile name) {}))))
  







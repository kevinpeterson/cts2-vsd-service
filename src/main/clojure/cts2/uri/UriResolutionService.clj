(ns cts2.uri
  (:require [clj-http.client :as client]))

(def call
  (memoize
   (fn [url response]
     (get
       (:body 
         (client/get url {:throw-exceptions false :accept :json :as :json})) (keyword response) ))))

(defn- doGetId 
 [baseuri type id resource]
    (call (str baseuri "/id/" type "?id=" id) resource))

(defn getUri 
 [baseuri type id]
    (doGetId baseuri type id "resourceURI"))

(defn getName 
 [baseuri type id]
    (doGetId baseuri type id "resourceName"))

(defn- doGetVersion
 [baseuri type identifier id resource]
    (call (str baseuri "/version/" type "/" identifier "/" id) resource))

(defn getVersionName 
  [url type identifier id]
    (doGetVersion url type identifier id "resourceName"))

(defn getVersionUri 
  [url type identifier id]
    (doGetVersion url type identifier id "resourceURI"))

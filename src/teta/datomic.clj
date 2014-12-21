(ns teta.datomic 
  (:require [datomic.api :as d]
            [questdb.core :as qc]))

(def uri "datomic:couchbase://localhost/alfa/datom")
(d/create-database uri)
(def conn (d/connect uri))
(def db (d/db conn))













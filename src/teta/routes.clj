(ns teta.routes
  (:require [compojure.core :refer :all]
            [teta.layout :as page]))

(defn homepage
  "The rendering function for homepage"
  []
  (page/render "home.html"
               {:headline "Welcome to ...."
                :title "Luminoob website"}))

(defroutes home
  (GET "/" req
       (homepage)))

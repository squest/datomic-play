(ns teta.core
	(:require [reagent.core :as re]
						[ajax.core :refer [GET POST]]))

(defn selid
	[id]
	(.getElementById js/document id))

(defn choice-form
	[]
	[:h1 "Welldone"])

(defn start
	[]
	(re/render-component [choice-form]
											 (selid "main")))

(start)



(ns teta.core
	(:require [reagent.core :as re]
						[ajax.core :refer [GET POST]]))

(defn selid
	[id]
	(.getElementById js/document id))



(defn choice-form
	[]
	(let [cheko (re/atom false)]
		(fn []
			[:form
			 [:fieldset.zpanel3
				[:legend "Woi"]
				[:br]
				[:input {:type    "radio"
								 :name    "this"
								 :value   "welldone"
								 :checked @cheko
								 :on-change #(reset! cheko (not @cheko))}
				 " Wellthen"]]])))

(defn start
	[]
	(re/render-component [choice-form]
											 (selid "main")))

(start)



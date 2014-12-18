(ns teta.core
	(:require [reagent.core :as re]
						[ajax.core :refer [GET POST]]))

(defn selid
	[id]
	(.getElementById js/document id))

(def soal (re/atom {}))

(defn post-answer-callback
	[resp]
	(js/alert (str (resp :user) " " (resp :message))))

(defn post-answer
	[st]
	(POST "/jawab"
				{:params {:user "dodol"
									:answer st}
				 :handler post-answer-callback}))

(defn soal-content
	[]
	(let [red "one"]
		(fn []
			[:form
			 [:fieldset.zpanel3
				[:legend "Woi"]
				[:h4 (@soal :soal)]
				[:br]
				(vec (cons :div (map #(vector :input.button.small
																			{:type     "button"
																			 :value %
																			 :on-click (fn []
																									 (post-answer %))}
																			[:br])
														 (@soal :options))))]])))

(defn soal-error
	[resp]
	(js/alert "Soal error woi"))

(defn soal-dateng
	[resp]
	(do (reset! soal resp)
			(re/render-component [soal-content]
													 (selid "main"))))

(defn get-soal
	[]
	(GET "/soal"
			 {:handler soal-dateng
				:error-handler soal-error}))

(defn start
	[]
	(get-soal))

(start)
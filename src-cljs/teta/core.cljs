(ns teta.core
	(:require [reagent.core :as re]
						[ajax.core :refer [GET POST]]))

(defn selid
	[id]
	(.getElementById js/document id))



(defn reload-mathjax
	[]
	js/reloadMathjax)

(def anim-duration 2000)

(defn trans-content
	[domelmt]
	(.fadeIn js/Jacked
					 (selid domelmt)
					 {:duration anim-duration}))

(def soal (re/atom {}))

(declare get-soal)

(def g-message (re/atom ""))

(defn jawaban-bener
	[]
	[:div.zpanel3
	 [:h4 @g-message]])

(defn post-answer-callback
	[resp]
	(let [{:keys [message stat]} resp]
		(if stat
			(do (reset! g-message message)
					(re/render-component [jawaban-bener]
															 (selid "main"))
					(trans-content "main")
					(js/setTimeout #(get-soal) 3000))
			(js/alert message))))

(defn post-answer
	[st er]
	(POST "/jawab"
				{:params {:user "dodol"
									:answer st
									:yangbener er}
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
				(->> (map #(vector :input.button.small
													 {:type     "button"
														:value    %
														:on-click (fn []
																				(post-answer % (@soal :answer)))}
													 [:br])
									(@soal :options))
						 (cons :div)
						 (vec))]])))

(defn soal-error
	[resp]
	(js/alert "Soal error woi"))

(defn soal-dateng
	[resp]
	(do (reset! soal resp)
			(re/render-component [soal-content]
													 (selid "main"))
			(trans-content "main")
			(reload-mathjax)))

(defn get-soal
	[]
	(GET "/soal"
			 {:handler soal-dateng
				:error-handler soal-error}))

(defn start
	[]
	(get-soal))

(start)

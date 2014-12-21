(ns teta-node.core
	(:require [cljs.nodejs :as node]))


(def express (js/require "express"))
(def app (express))

(defn main
	[]
	(do (.get app "/"
						(fn [req res]
							(.send res "Heloow world")))
			(.get app "/codingan"
						(fn [req res]
							(.send res "Hwllow world")))
			(.listen app 8000
							 (fn []
								 (.log js/console "App is started")))))

(main)

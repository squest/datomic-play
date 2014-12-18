(ns teta.routes
  (:require [compojure.core :refer :all]
            [teta.layout :as page]
            [noir.response :as resp]))

(defn homepage
  "The rendering function for homepage"
  []
  (page/render "home.html"
               {:headline "Welcome to ...."
                :title "Luminoob website"}))

(defn get-soal
  []
  (let [choices [{:soal    "Coba ya kerjain yang satu ini $ \\lim\\limits_{x->\\infty} x^2 $"
                  :options ["well" "will" "wooll" "welllo" "book"]
                  :answer  "well"}
                 {:soal    "Coba ya kerjain yang satu ini"
                  :options ["well" "will" "wooll" "welllo" "book"]
                  :answer  "wooll"}]]
    (rand-nth choices)))

(defroutes home
  (GET "/" req
       (homepage))
  (GET "/soal" req
       (resp/edn (get-soal)))
  (POST "/jawab" req
        (let [{:keys [answer user yangbener]} (:params req)]
          (if (= answer yangbener)
            (resp/edn {:user user :message "Pinteerr" :stat true})
            (resp/edn {:user user :message "ANJING GUOBLOAK SIA!!" :stat false})))))

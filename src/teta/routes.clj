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
  {:soal "Coba ya kerjain yang satu ini $ \\lim\\limits_{x->\\infty} x^2 $"
   :options ["well" "will" "wooll" "welllo" "book"]
   :answer "well"})

(defroutes home
  (GET "/" req
       (homepage))
  (GET "/soal" req
       (resp/edn (get-soal)))
  (POST "/jawab" req
        (let [{:keys [answer user]} (:params req)]
          (if (= answer (:answer (get-soal)))
            (resp/edn {:user user :message "Pinteerr"})
            (resp/edn {:user user :message "ANJING GUOBLOAK SIA!!"})))))

(ns teta.routes
  (:require [compojure.core :refer :all]
            [teta.layout :as page]
            [noir.response :as resp]
            [org.httpkit.client :as http]))

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

(def answers (atom {}))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn soal
  []
  (let [lst (shuffle (map #(* % %) (range (rand-int 200) 300)))
        lim (rand-nth (range 50 100))]
    {:take lim
     :list lst
     :answer
           (reduce + (take lim lst))
     :uuid (uuid)}))

(defroutes home
           (GET "/" req
                (homepage))
           (GET "/soal" req
                (resp/edn (get-soal)))
           (GET "/brutal" req
                (let [soso (soal)
                      well (future (Thread/sleep 1)
                              (swap! answers dissoc (:uuid soso)))]
                  (do (swap! answers assoc (:uuid soso) (str (:answer soso)))
                      (resp/edn (dissoc soso :answer)))))
           (POST "/brutal" req
                 (let [heihei (:params req)
                       respon (get @answers (:uuid heihei) -100)]
                   (do (println (:params req)
                                (string? (:uuid heihei))
                                (string? (:answer heihei)))
                       (if (= (:answer heihei) respon) "Yoih brohter" "GUOBLOK"))))
           (POST "/jawab" req
                 (let [{:keys [answer user yangbener]}
                       (:params req)]
                   (if (= answer yangbener)
                     (resp/edn {:user user
                                :message "Pinteerr"
                                :stat true})
                     (resp/edn {:user user
                                :message "ANJING GUOBLOAK SIA!!"
                                :stat false})))))

(defn jawab
  []
  (let [data (-> @(http/get
                    "http://localhost:3000/brutal"
                    {:as :text})
                 (:body)
                 (read-string))]
    (-> @(http/post "http://localhost:3000/brutal"
                    {:form-params
                     {:uuid   (:uuid data)
                      :answer (->> (:list data)
                                   (take (:take data))
                                   (reduce +))}
                     :as :text})
        (:body))))




















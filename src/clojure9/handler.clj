(ns clojure9.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]                                             
            [clojure9.res-users :as res-users]
            [clojure9.tree :as arvore]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
           (GET "/" [] "Hello World")

           (GET "/tree" [] (arvore/get-tree) )

           (GET "/res-users" [] (res-users/get-all))
           (route/not-found "Not Found"))

;(def app
;  (wrap-defaults app-routes site-defaults))

;(defroutes all-routes
;           (GET "/" [] "Hello World")
;           (route/not-found "Página não encontrada :("))




(def app
  (-> app-routes
      wrap-json-response
      wrap-json-body))

(->> (res-users/get-all)
     (vec)
     (filter #(= (:login %) "demo")))

(->> (res-users/get-all)
     (vec)
     (filter #(= (:id %) 7)))

(res-users/get-all)

(first (res-users/get-all))

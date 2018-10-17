(ns clojure9.res-users
  (:require [korma.db :refere :all]
            [korma.core :refer :all]
            [clojure9.db :refer :all])
  (:refer-clojure :exclude [update]))


(defentity res_users)

(defentity res_partner)

(defn find-all []
  (-> (select res_users)
      (fields :id :login)))

(defn get-all []
  (-> (select res_users)))


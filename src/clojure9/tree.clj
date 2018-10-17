(ns clojure9.tree
  (:require [korma.db :refere :all]
            [korma.core :refer :all]
            [clojure.data.json :as json]
            [clojure9.db :refer :all])
  (:refer-clojure :exclude [update]))

(defn make-tree []
  (json/write-str {:name "Hendrix" :node {:x 21231 :y 23131 :id 00001}}))

(defn get-tree [] (make-tree))



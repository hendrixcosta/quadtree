(ns clojure9.tree
  (:require [korma.db :refere :all]
            [korma.core :refer :all]
            [clojure.data.json :as json]
            [clojure9.db :refer :all])
  (:refer-clojure :exclude [update]))

(defrecord Point
  [x y data])

(defrecord QuadTreeNode
  [boundary points northWest northEast southWest southEast maxPoints])


(defn insert-node [tree point]

  (cond
    ;Se o ponto nao esta na regiao da arvore retorna arvore original
    (not (q-contains? (-> tree :boundary) point)) tree

    ;se a tree for uma folha e os pontos nao atingiram a quantidade maxima
    (and (leaf? tree)
         (< (count (-> tree :points)) (-> tree :maxPoints)))

      ;Criar nova arvore com os mesmos limites de região, adicionando o ponto
      ;do parametro ao conjunto de pontos
      (QuadTreeNode. (-> tree :boundary) (conj (-> tree :points) point)
                   nil nil nil nil (-> tree :maxPoints))

    (not (leaf? tree))

    ;(defrecord QuadTreeNode
    ;  [boundary points northWest northEast southWest southEast maxPoints])

      (QuadTreeNode. (-> tree :boundary) []
                   (insert (-> tree :northWest) point)
                   (insert (-> tree :northEast) point)
                   (insert (-> tree :southWest) point)
                   (insert (-> tree :southEast) point)
                   (-> tree :maxPoints))
    :else
    (let [child-nodes (subdivide tree)]
      (cond (q-contains? (-> child-nodes :northWest :boundary) point)
            (QuadTreeNode. (-> tree :boundary) []
                           (insert (-> child-nodes :northWest) point)
                           (-> child-nodes :northEast)
                           (-> child-nodes :southWest)
                           (-> child-nodes :southEast)
                           (-> tree :maxPoints))
            (q-contains? (-> child-nodes :northEast :boundary) point)
            (QuadTreeNode. (-> tree :boundary) []
                           (-> child-nodes :northWest)
                           (insert (-> child-nodes :northEast) point)
                           (-> child-nodes :southWest)
                           (-> child-nodes :southEast)
                           (-> tree :maxPoints))
            (q-contains? (-> child-nodes :southWest :boundary) point)
            (QuadTreeNode. (-> tree :boundary) []
                           (-> child-nodes :northWest)
                           (-> child-nodes :northEast)
                           (insert (-> child-nodes :southWest) point)
                           (-> child-nodes :southEast)
                           (-> tree :maxPoints))
            :else
            (QuadTreeNode. (-> tree :boundary) []
                           (-> child-nodes :northWest)
                           (-> child-nodes :northEast)
                           (-> child-nodes :southWest)
                           (insert (-> child-nodes :southEast) point)
                           (-> tree :maxPoints))))))

(defn make-tree []
  (json/write-str {:name "Hendrix" :node {:x 21231 :y 23131 :id 00001}}))

(defn get-tree [] (make-tree))



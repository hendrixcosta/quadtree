(ns clojure9.db
  (:use korma.db))

(defdb pg (postgres
            {:host "localhost"
            :port "5432"
            :db "l10n-br-hr-contract"
            :user "odoo"
            :password "odoo"
            :delimiters "" }))



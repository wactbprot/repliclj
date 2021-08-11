(ns wactbprot.replhub.cli
  (:require [wactbprot.replhub.db :as db]
            [wactbprot.replhub.conf :as conf])
  (:gen-class))

(comment
  (get-repl-doc conf/conf))

(defn get-repl-doc [{id :repl-doc :as conf}] (db/get-doc conf id))

(defn gen-db-usr [conf conn] (db/gen-usr conf conn))  
  
(defn -main
  "Say Hello!"
  [& args]
  (println conf/conf))


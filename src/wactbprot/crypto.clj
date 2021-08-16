(ns  wactbprot.repliclj.crypto
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Simple encode/decode function (AES/128) from 
          https://stackoverflow.com/questions/10221257/is-there-an-aes-library-for-clojure."}
  (:require [wactbprot.repliclj.conf :as conf])
  (:import [javax.crypto Cipher KeyGenerator SecretKey]
           [javax.crypto.spec SecretKeySpec]
           [java.security SecureRandom]
           [org.apache.commons.codec.binary Base64]))

(defn bytes [s] (.getBytes s "UTF-8"))

(defn base64 [b] (Base64/encodeBase64String b))
  
(defn debase64 [s] (Base64/decodeBase64 (bytes s)))

(defn get-raw-key [seed]
  (let [kg (KeyGenerator/getInstance "AES")
        sr (SecureRandom/getInstance "SHA1PRNG")]
    (.setSeed sr (bytes seed))
    (.init kg 128 sr)
    (.. kg generateKey getEncoded)))

(defn get-cipher [mode seed]
  (let [cipher (Cipher/getInstance "AES")]
    (.init cipher mode (SecretKeySpec. (get-raw-key seed) "AES"))
    cipher))

(defn encrypt [s secret]
  (let [cipher (get-cipher Cipher/ENCRYPT_MODE secret)]
    (base64 (.doFinal cipher (bytes s)))))

(defn decrypt [s secret]
  (let [cipher (get-cipher Cipher/DECRYPT_MODE secret)]
    (String. (.doFinal cipher (debase64 s)))))

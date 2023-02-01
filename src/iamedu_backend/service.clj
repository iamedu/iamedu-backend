(ns iamedu-backend.service
  (:require [io.pedestal.http :as http]
            [com.walmartlabs.lacinia.pedestal2 :as lp]
            [com.walmartlabs.lacinia.schema :as schema])
  (:import (org.eclipse.jetty.server.handler.gzip GzipHandler)))

(def hello-schema
  (schema/compile
    {:queries
     {:hello
      ;; String is quoted here; in EDN the quotation is not required
      ;; You could also use :String
      {:type 'String
       :resolve (constantly "world")}}}))

(def service
  (merge
    (lp/default-service hello-schema {:port 8080})
    {::http/container-options {:h2c? true
                               :h2? false
                               ;:keystore "test/hp/keystore.jks"
                               ;:key-password "password"
                               ;:ssl-port 8443
                               :ssl? false
                               ;; Alternatively, You can specify your own Jetty HTTPConfiguration
                               ;; via the `:io.pedestal.http.jetty/http-configuration` container option.
                               ;:io.pedestal.http.jetty/http-configuration (org.eclipse.jetty.server.HttpConfiguration.)
                               :context-configurator (fn [c]
                                                       (let [gzip-handler (GzipHandler.)]
                                                         (.setGzipHandler c gzip-handler)
                                                         (.addIncludedMethods gzip-handler (into-array ["GET" "POST"]))))
                               }}))

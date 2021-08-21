(ns trustblocks.swagger
(:require  [reitit.ring :as ring]
           [reitit.swagger :as swagger]
           [reitit.swagger-ui :as swagger-ui]
           [reitit.coercion.spec :as coercion-spec]
           [reitit.ring.coercion :as coercion]
           [reitit.ring.middleware.exception :as exception]
           [reitit.ring.middleware.muuntaja :as muuntaja]
           [muuntaja.core :as m]))



(def swagger-middleware [swagger/swagger-feature])
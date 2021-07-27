(ns trustblocks.awsclient
  (:require
    [cognitect.aws.client.api :as aws]
     [cognitect.aws.credentials :as creds]))  


 (def s3 (aws/client {:api :s3}))

 
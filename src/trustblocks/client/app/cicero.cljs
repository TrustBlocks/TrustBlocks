(ns trustblocks.client.app.cicero
  (:require
   [applied-science.js-interop :as j]
   ["@accordproject/cicero-core": "0.22.0"]))

(def note (.new Clause ../templates/promissory-note/grammar.tem.md))
(type note)

(def car "pontiac")
(type car)
(comment
  
   // js code from: https://github.com/accordproject/aws-qldb-lambda
   // load the template
  
            const contractFilePath = `${TARGET_DIR}/${contractFileName}`;

            const templateData = await s3.downloadAsFile(contractSourceS3BucketObjectPath, contractFilePath);

            if (!templateData) {
                throw new Error(`Did not find an active contract ${contractId}. Ensure it has been uploaded to S3. (1)`);
            }

            // check that the template is valid
  
            logger.debug(`${fcnName} Contract source downloaded. Unzipping.`);
            const contractUnzipFolder = `${TARGET_DIR}/contract`;
            await Utils.__unzip(contractFilePath, contractUnzipFolder)
            const template = await Template.fromDirectory(contractUnzipFolder); //Buffer.from(templateDataString, 'base64'));
            logger.info(`${fcnName} Loaded template: ${template.getIdentifier()}`);

            // validate and save the contract data
   
            const clause = new Clause(template);
            clause.setData(JSON.parse(contractData));
  )
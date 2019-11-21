# GCS2BQ Pipeline

GCS to BigQuery pipeline demo. 

### Test
```bash
gradle :apps:gcs-to-bq:test
```

### Run

#### Local Run  
```bash
gradle :apps:gcs-to-bq:run

gradle :apps:gcs-to-bq:run --args="--runner=DirectRunner --inputGCSPath=./src/test/resources/data/simple.json --outputBigQueryTable=aaa.bbb"

PROJECT_ID=<my-project-id>
GCS_BUCKET=<my-project-gcs-bucket>
GOOGLE_APPLICATION_CREDENTIALS=<full-path-to-your-json>

gradle :apps:gcs-to-bq:run --args="--runner=DataflowRunner --tempLocation=gs://<your-bucket>"
gradle :apps:gcs-to-bq:run --args="--runner=DataflowRunner --project='$PROJECT_ID' --stagingLocation=gs://$GCS_BUCKET/staging --templateLocation=gs://$GCS_BUCKET/dataflow/templates/gcs_batch_process"

```

Run DataFlow pipeline with:

```bash
PROJECT_ID=my-project-name
GCS_BUCKET=my-project-gcs-bucket-203291974
GOOGLE_APPLICATION_CREDENTIALS=CREDENTIALS_FILE.json
gradle run -Dorg.gradle.java.home="/usr/lib/jvm/java-8-openjdk-amd64" \
--args="--runner=DataFlowRunner --project='$PROJECT_ID' --tempLocation='gs://$GCS_BUCKET/dataflow' \
    --subnetwork=https://www.googleapis.com/compute/alpha/projects/$PROJECT_ID/regions/europe-west4/subnetworks/my-vpc-subnet \
    --region=europe-west1 --zone=europe-west4-a --usePublicIps=false --inputGCSPath=gs://$GCS_BUCKET/data/sample.json"
```

Deploy as template with:
```bash
gradle run -Dorg.gradle.java.home="/usr/lib/jvm/java-8-openjdk-amd64" \
--args="--runner=DataflowRunner --project='$PROJECT_ID' \
    --stagingLocation=gs://$GCS_BUCKET/staging \
    --templateLocation=gs://$GCS_BUCKET/dataflow/templates/gcs_batch_process"
```

### Build
```bash
# display version
gradle :versionDisplay
# clean
gradle :apps:gcs-to-bq:clean
# make fatJar
gradle :apps:gcs-to-bq:build
# docker build
gradle :apps:gcs-to-bq:jibDockerBuild
```

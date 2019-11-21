# WordCount

WordCount pipeline demo. 

### Test
```bash
gradle :apps:wordcount:test
```

### Run

#### Local Run  
```bash
gradle :apps:wordcount:run --args="--runner=DirectRunner --inputFile=./src/test/resources/data/input.txt --output=./build/output.txt"
```
 
#### Cloud Run  
```bash
PROJECT_ID=<my-project-id>
GCS_BUCKET=<my-project-gcs-bucket>
export GOOGLE_APPLICATION_CREDENTIALS=<full-path-to-your-json>

gradle :apps:wordcount:run --args="--runner=DataflowRunner --project=$PROJECT_ID --gcpTempLocation=gs://$GCS_BUCKET/dataflow/wordcount/temp/ --stagingLocation=gs://$GCS_BUCKET/dataflow/wordcount/staging/ --inputFile=gs://$GCS_BUCKET/dataflow/wordcount/input/shakespeare.txt --output=gs://$GCS_BUCKET/dataflow/wordcount/output/output.txt"

# Or with fatJar
java -jar /Users/schintha/Developer/Work/java/dataflow/apps/wordcount/build/libs/wordcount-master-8a13900-dirty-all.jar \
--runner=DataflowRunner --project=$PROJECT_ID \
--gcpTempLocation=gs://$GCS_BUCKET/dataflow/wordcount/temp/ \
--stagingLocation=gs://$GCS_BUCKET/dataflow/wordcount/staging/ \
--inputFile=gs://$GCS_BUCKET/dataflow/wordcount/input/shakespeare.txt \
--output=gs://$GCS_BUCKET/dataflow/wordcount/output/output.txt
```

#### Deploy Template
```bash
gradle :apps:wordcount:run --args="--runner=DataflowRunner --project=$PROJECT_ID --gcpTempLocation=gs://$GCS_BUCKET/dataflow/wordcount/temp/ --stagingLocation=gs://$GCS_BUCKET/dataflow/wordcount/staging/ --templateLocation=gs://$GCS_BUCKET/dataflow/wordcount/template/"
```

#### Run Template
> Create Job
```bash
gradle :apps:wordcount:run --args="--project=$PROJECT_ID --gcpTempLocation=gs://$GCS_BUCKET/dataflow/wordcount/temp/ --stagingLocation=gs://$GCS_BUCKET/dataflow/wordcount/staging/ --templateLocation=gs://$GCS_BUCKET/dataflow/wordcount/template/"
```

### Build
```bash
# display version
gradle :versionDisplay
# clean
gradle :apps:wordcount:clean
# make fatJar
gradle :apps:wordcount:build
# docker build
gradle :apps:wordcount:jibDockerBuild
```

rootProject.name = "dataflow"
include(
        ":libs:core",
        ":libs:shared",
        ":apps:demo",
        ":apps:wordcount",
        ":apps:gcs-to-bq"
)
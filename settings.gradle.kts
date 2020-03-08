rootProject.name = "beam-examples"
include(
        ":libs:core",
        ":libs:shared",
        ":apps:wordcount",
        ":apps:gcs-to-bq"
)
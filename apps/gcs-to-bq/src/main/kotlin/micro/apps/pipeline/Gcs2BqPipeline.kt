package micro.apps.pipeline

import com.google.api.services.bigquery.model.TableRow
import com.google.common.flogger.FluentLogger
import micro.apps.core.model.DataMessage
import micro.apps.core.util.gsonBuilder
import micro.apps.core.util.toISO8601
import micro.apps.shared.dsl.KPipeline
import micro.apps.shared.dsl.fromText
import micro.apps.shared.dsl.map
import micro.apps.shared.dsl.toBigquery
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions
import org.apache.beam.sdk.options.Default
import org.apache.beam.sdk.options.Description

interface Gcs2BqPipelineOptions : DataflowPipelineOptions {
    @get:Description("Path of the file to read from")
    @get:Default.String("non-existent-bucket")
    var inputGCSPath: String

    @get:Description("BigQuery dataset and table name")
    @get:Default.String("dataset_name.table_name")
    var outputBigQueryTable: String?
}


object Gcs2BqPipeline {

    private val logger = FluentLogger.forEnclosingClass()

    @JvmStatic
    fun main(args: Array<String>) {

        val (pipe, options) = KPipeline.from<Gcs2BqPipelineOptions>(args)


        val (dataset, table) = options.outputBigQueryTable?.split(".").orEmpty()

        logger.atInfo().log(
                """Runner: ${options.runner.name}
                    |Job name: ${options.jobName}
                """.trimMargin()
        )

        pipe
                .fromText("Read from GCS", options.inputGCSPath)
                .map("Insert into BigQuery") { line ->
                    val message = gsonBuilder().fromJson<DataMessage>(line, DataMessage::class.java)
                    TableRow()
                            .set("id", message.id)
                            .set("name", message.name)
                            .set("location", message.location)
                            .set("price", message.price)
                            .set("timestamp", message.timestamp.toISO8601())
                }
                .toBigquery(table = table,
                        dataset = dataset,
                        project = options.project)

        // Run the pipeline
        pipe.run().waitUntilFinish()

    }

}

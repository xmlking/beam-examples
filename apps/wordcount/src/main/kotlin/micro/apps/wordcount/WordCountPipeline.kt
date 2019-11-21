package micro.apps.wordcount

import com.google.common.flogger.FluentLogger
import micro.apps.shared.dsl.*
import org.apache.beam.sdk.options.*

interface WordCountOptions : PipelineOptions {
    @get:Description("Path of the file to read from")
    @get:Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
    @get:Validation.Required
    val inputFile: ValueProvider<String>

    fun setInputFile(inputFile: ValueProvider<String>)

    @get:Description("Path of the file to write to")
    @get:Default.String("gs://apache-beam-samples/shakespeare/output/output.txt")
    @get:Validation.Required
    val output: ValueProvider<String>

    fun setOutput(output: ValueProvider<String>)
}

const val TOKENIZER_PATTERN = "[^\\p{L}]+"

object WordCountPipeline {

    private val logger = FluentLogger.forEnclosingClass()

    @JvmStatic
    fun main(args: Array<String>) {

        logger.atConfig().log("My Args: %s", args);

        val (pipe, options) = KPipeline.from<WordCountOptions>(args)

        logger.atInfo().log(
                """Runner: ${options.runner.name}
                    |Job name: ${options.jobName}
                """.trimMargin()
        )

        pipe.fromText(path = options.inputFile.get())
                .flatMap { it.split(Regex(TOKENIZER_PATTERN)).filter { it.isNotEmpty() }.toList() }
                .countPerElement()
                .map { "${it.key}: ${it.value}" }
                .toText(filename = options.output.get())

        pipe.run().waitUntilFinish()
    }
}
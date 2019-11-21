package micro.apps.pipeline

import org.apache.beam.sdk.testing.TestPipeline
import org.junit.Rule
import java.io.Serializable
import kotlin.test.Test


class WordCountTest : Serializable {

    @Rule
    @Transient
    @JvmField
    val pipeline = TestPipeline.create()

    @Test
    fun `Should read GCS and store into BigQuery`() {
        // TODO

        pipeline.run()
    }
}
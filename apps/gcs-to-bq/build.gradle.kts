plugins {
    application
    id("com.github.johnrengelman.shadow")
}

val kotlinVersion: String by project
val beamVersion: String by project
val csvVersion: String by project

dependencies {
    implementation(project(":libs:core"))
    implementation(project(":libs:shared"))

    // Use Apache Beam
    implementation("org.apache.beam:beam-sdks-java-core:$beamVersion")
    implementation("org.apache.beam:beam-runners-direct-java:$beamVersion")
    implementation("org.apache.beam:beam-runners-google-cloud-dataflow-java:$beamVersion")
    implementation("org.apache.beam:beam-sdks-java-io-google-cloud-platform:$beamVersion")
    implementation("org.apache.commons:commons-csv:$csvVersion")
}

application {
    mainClassName = "micro.apps.pipeline.Gcs2BqPipeline"
//    applicationDefaultJvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1")
}

jib {
    to {
        image = "xmlking/${rootProject.name}-${project.name}:${project.version}"
        // image = "gcr.io/${gcloudProject}/${project.name}:${project.version}"
    }
}

tasks {
    shadowJar {
        manifest {
            attributes += mapOf("Implementation-Title" to project.name, "Implementation-Version" to project.version)
        }
    }
    jar {
        enabled = false
    }
}
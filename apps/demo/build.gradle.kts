plugins {
    application
}


dependencies {
    implementation(project(":libs:core"))
}


application {
    mainClassName = "micro.apps.demo.AppKt"
//    applicationDefaultJvmArgs = listOf(
//            "-noverify", "-XX:TieredStopAtLevel=1")
}

jib {
    to {
        image = "xmlking/${rootProject.name}-${project.name}:${project.version}"
        // image = "gcr.io/${gcloudProject}/${project.name}:${project.version}"
    }
}
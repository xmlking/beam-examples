import net.nemerosa.versioning.SCMInfo

val kotlinVersion: String by project
val floggerVersion: String by project
val hamcrestVersion: String by project

plugins {
    base
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.60"
    id("net.nemerosa.versioning") version "2.9.0"
    id("com.google.cloud.tools.jib") version "1.8.0" apply false
    id("com.github.johnrengelman.shadow") version "5.2.0" apply false
}

java {
    // Java 8 needed as Beam doesn't yet support 11
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

versioning {
    dirty = KotlinClosure1<String, String>({ this })
    full = KotlinClosure1<SCMInfo, String>({ "${this.lastTag}-${this.branch}-${this.abbreviated}" })
    noWarningOnDirty = true
}

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("net.nemerosa.versioning")
    }

    repositories {
//        mavenLocal()
//        mavenCentral()
        jcenter()
        google()
    }
    version = versioning.info.full
}

subprojects {

    plugins.withId("application") {
        project.apply { plugin("com.google.cloud.tools.jib") }
    }

    dependencies {
        // Align versions of all Kotlin components
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

        // Use the Kotlin JDK 8 standard library.
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))

        // Use the Kotlin test library.
        testImplementation(kotlin("test"))

        // Use the Kotlin JUnit integration.
        testImplementation(kotlin("test-junit"))
        testImplementation("org.hamcrest:hamcrest-all:$hamcrestVersion")

        // Logging
        implementation("com.google.flogger:flogger:$floggerVersion")
        runtimeOnly("com.google.flogger:flogger-system-backend:$floggerVersion")
    }

    tasks {
        test {
            testLogging.showExceptions = true
        }
    }
}




import java.io.IOException
import java.util.Properties

val kotlinLoggingVersion: String by ext
val springCloudVersion: String by ext
val testContainerVersion: String by ext
val resilience4jVersion: String by ext
val kotlinxCoroutinesVersion: String by ext
val kotlinVersion: String by ext
val log4j2Version: String by ext
val jacksonVersion: String by ext
val mockkVersion: String by ext
val mockServerClientJavaVersions: String by ext

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    jacoco
}

group = "soat7.group61.myvideonotify"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

val props = Properties()
try {
    props.load(file("$projectDir/.env").inputStream())
} catch (e: IOException) {
    println(e.message)
}

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("io.spring.dependency-management")
    plugin("org.jlleitschuh.gradle.ktlint")
    plugin("jacoco")
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

// configurations {
//    all {
//        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
//        exclude(group = "ch.qos.logback", module = "logback-classic")
//        exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
//        exclude(group = "io.arrow-kt", module = "arrow-core-extensions")
//        exclude(group = "io.projectreactor.netty", module = "reactor-netty-http-brave")
//    }
// }

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("io.github.resilience4j:resilience4j-bom:$resilience4jVersion")
        mavenBom("org.testcontainers:testcontainers-bom:$testContainerVersion")
    }

    dependencies {
        dependency("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
        dependency("io.mockk:mockk:$mockkVersion")
    }
}

ext["kotlin.version"] = kotlinVersion
ext["kotlin-coroutines.version"] = kotlinxCoroutinesVersion
ext["log4j2.version"] = log4j2Version
ext["jackson-bom.version"] = jacksonVersion

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.mailersend:java-sdk:1.0.0")
    implementation("io.nats:nats-spring-boot-starter:0.6.0-3.1")
    implementation("io.github.microutils:kotlin-logging")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
    testImplementation("io.mockk:mockk")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks {
    jar {
        enabled = false
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events = setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
            )
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }

    jacocoTestReport {
        reports {
            xml.required = true
            csv.required = true
            html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/test/html"))
            xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacoco.xml"))
            csv.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacoco.csv"))
            classDirectories.setFrom(
                files(
                    classDirectories.files.map {
                        fileTree(it).apply {
                            exclude("**/mapper/**")
                            exclude("**/model/**")
                            exclude("**/api/**")
                            exclude("**/enum/**")
                            exclude("**/config/**")
                            exclude("**/common/**")
                            exclude("**/exception/*")
                            exclude("*/Application*")
                        }
                    }
                )
            )
        }
        dependsOn(withType<Test>())
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = 0.80.toBigDecimal()
                }
            }
        }
    }

    check {
        dependsOn(jacocoTestCoverageVerification)
    }
}

ktlint {
    debug.set(false)
    this.coloredOutput.set(true)
    this.outputToConsole.set(true)
}

springBoot {
    buildInfo()
}

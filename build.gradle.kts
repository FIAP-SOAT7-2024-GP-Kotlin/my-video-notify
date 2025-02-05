import java.io.IOException
import java.util.Properties

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
    id("org.liquibase.gradle") version "3.0.1"
}

group = "soat7.group61"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

buildscript {
    dependencies {
        classpath("org.liquibase:liquibase-core:4.+")
    }
}

val props = Properties()
try {
    props.load(file("$projectDir/.env").inputStream())
} catch (e: IOException) {
    println(e.message)
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core:4.+")
    implementation("org.postgresql:postgresql:42.7.+")
	implementation("com.mailersend:java-sdk:1.0.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

configurations {
    liquibaseRuntime {
        extendsFrom(configurations.compileClasspath.get())
    }
}

liquibase {
    this.activities.register("update") {
        this.arguments = mapOf(
            "changelogFile" to "/db/changelog/master.xml",
            "searchPath" to sourceSets.main.get().resources.srcDirs.first(),
            "url" to props["DATABASE_URL"],
            "username" to props["DATABASE_USER"],
            "password" to props["DATABASE_PASSWORD"],
            "logLevel" to "debug"
        )
    }
    runList = "update"
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

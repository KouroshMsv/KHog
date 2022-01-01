val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    `maven-publish`
    kotlin("jvm") version "1.6.10"
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.kourosh"
            artifactId = "objectGenerator"
            version = "0.0.3"

            from(components["java"])
        }
    }
}
group = "dev.kourosh"
version = "0.0.3"
application {
    mainClass.set("dev.kourosh.ApplicationKt")
}

repositories {
    mavenCentral()
}
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}
dependencies {
    implementation(project(":ObjectGenerator"))
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation(kotlin("script-runtime"))
}
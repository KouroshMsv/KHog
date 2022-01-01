plugins {
    kotlin("jvm")
    `maven-publish`
}
group = "com.github.KouroshMsv"
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.KouroshMsv"
            artifactId = "objectGenerator"
            version = "0.0.7"

            from(components["java"])
        }
    }
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:2.0.0-beta-1")
    implementation("com.squareup:kotlinpoet:1.10.2"){
        exclude(module = "kotlin-reflect")
    }
}
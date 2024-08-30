import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    id("com.github.gmazzo.buildconfig") version "5.4.0"
}

buildConfig {
    buildConfigField("OCR_SERVER_HOST", getLocalProperty("OCR_SERVER_HOST") ?: "0.0.0.0")
    buildConfigField("OCR_SERVER_PORT", getLocalProperty("OCR_SERVER_PORT") ?: "8081")
}

group = "com.ccc.resume"
version = "1.0.0"
application {
    mainClass.set("com.ccc.resume.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}


dependencies {
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}

tasks.register("runAll") {
    dependsOn(tasks.getByPath(":composeApp:wasmJsDevelopmentExecutableCompileSync"))
    dependsOn(tasks.run)
}
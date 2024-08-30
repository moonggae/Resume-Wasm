import org.jetbrains.compose.internal.utils.getLocalProperty
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    id("com.github.gmazzo.buildconfig") version "5.4.0"
}

buildConfig {
    fun getOcrServer(): String {
        var host = getLocalProperty("OCR_SERVER_HOST")
        if (host == "0.0.0.0") { host = "localhost" }
        val port = getLocalProperty("OCR_SERVER_PORT")
        return if (host.isNullOrBlank()) ""
        else if (port.isNullOrBlank()) host
        else "$host:$port"
    }

    buildConfigField("OCR_SERVER", getOcrServer())
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(compose.materialIconsExtended)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.emoji)
            implementation(libs.emoji.material)
            implementation(libs.ktor.client.core)
        }

        wasmJsMain.dependencies {
            implementation(npm("jspdf", "2.5.1"))
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.ccc.resume.resources"
    generateResClass = always
}

tasks.register("runAll") {
    dependsOn(tasks.getByPath("wasmJsDevelopmentExecutableCompileSync"))
}
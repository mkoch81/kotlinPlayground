plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.8.21"
    id("com.android.library")
    id("com.squareup.sqldelight")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {

        val ktorVersion = "2.3.1"
        val sqlDelightVersion = "1.5.5"
        val dateTimeVersion = "0.4.0"
        val coroutinesVersion = "1.6.4"

        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
                implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}:")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}")
                implementation("com.squareup.sqldelight:runtime:${sqlDelightVersion}")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${dateTimeVersion}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:${ktorVersion}")
                implementation("com.squareup.sqldelight:android-driver:${sqlDelightVersion}")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:${ktorVersion}")
                implementation("com.squareup.sqldelight:native-driver:${sqlDelightVersion}")
            }
        }
    }
}

android {
    namespace = "com.example.playground"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.playground.kmm.shared.entity"
    }
}
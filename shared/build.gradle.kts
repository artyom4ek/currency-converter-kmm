import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.serializationPlugin
    id("com.codingfeline.buildkonfig")
    id("com.android.library")
}

kotlin {
    android()

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
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Common.Core.coroutines)
                api(Dependencies.Common.Koin.core)
                with(Dependencies.Common.Ktor) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(serialization)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Android.Core.viewModel)
                implementation(Dependencies.Android.Ktor.client)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependencies.Ios.Ktor.client)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

buildkonfig {
    packageName = "com.paypay.currency_converter"
    defaultConfigs {
        buildConfigField(Type.STRING, "APP_ID", project.properties["APP_ID"].toString())
    }
}

android {
    compileSdk = DefaultConfig.compileSdk
    buildToolsVersion = DefaultConfig.buildToolsVersion
    defaultConfig {
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

dependencies {
    testImplementation(Dependencies.Common.Test.jUnit)
}

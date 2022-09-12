plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.codingfeline.buildkonfig")
    id("com.squareup.sqldelight")
    id("com.android.library")
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios") {
            binaries {
                framework {
                    baseName = "shared"
                }
            }
        }
    } else {
        iosX64("ios") {
            binaries {
                framework {
                    baseName = "shared"
                }
            }
        }
    }

    iosSimulatorArm64 {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Dependencies.Common.Core) {
                    implementation(coroutines)
                    implementation(dateTime)
                    implementation(bigNum)
                    implementation(multiplatformSettings)
                }
                api(Dependencies.Common.Koin.core)
                with(Dependencies.Common.Ktor) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(serialization)
                }
                implementation(Dependencies.Common.SqlDelight.runtime)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                with(Dependencies.Android.Core) {
                    implementation(coreKtx)
                    implementation(viewModel)
                }
                implementation(Dependencies.Android.Ktor.client)
                implementation(Dependencies.Android.SqlDelight.androidDriver)
            }
        }
        val androidTest by getting
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.Ios.Ktor.client)
                implementation(Dependencies.Ios.SqlDelight.nativeDriver)
            }
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }
    }
}

buildkonfig {
    packageName = "com.paypay.currency_converter"
    defaultConfigs {
        buildConfigField(Type.STRING, "APP_ID", project.properties["APP_ID"].toString())
    }
}

sqldelight {
    database("CurrencyDatabase") {
        packageName = "com.paypay.currency_converter.db"
        sourceFolders = listOf("sqldelight")
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

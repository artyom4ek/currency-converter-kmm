import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.codingfeline.buildkonfig")
    id("com.squareup.sqldelight")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
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
                with(Dependencies.Common.Koin) {
                    api(core)
                    api(test)
                }
                with(Dependencies.Common.Ktor) {
                    implementation(core)
                    implementation(contentNegotiation)
                    implementation(serialization)
                }
                implementation(Dependencies.Common.SqlDelight.runtime)
                api(Dependencies.Common.Core.mokoResources)
            }
        }
        val commonTest by getting {
            dependencies {
                with(Dependencies.Common.Test) {
                    implementation(kotlin(testCommon))
                    implementation(kotlin(testAnnotationsCommon))
                }
                implementation(Dependencies.Common.Koin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                with(Dependencies.Android.Core) {
                    implementation(coreKtx)
                    implementation(viewModel)
                    implementation(runtimeCompose)
                    implementation(guava)
                }
                with(Dependencies.Android.Ktor) {
                    implementation(client)
                    implementation(okHttp)
                }
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

    // Export correct artifact to use all classes of moko-resources directly from Swift
    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export(Dependencies.Common.Core.mokoResources)
        }
    }
}

buildkonfig {
    packageName = "com.paypay.currency_converter"
    defaultConfigs {
        buildConfigField(
            Type.STRING,
            "APP_ID",
            gradleLocalProperties(rootDir).getProperty("APP_ID")
        )
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.paypay.currency_converter"
    disableStaticFrameworkWarning = true
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
    testImplementation(Dependencies.Common.Test.testng)
}
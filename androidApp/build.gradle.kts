plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = DefaultConfig.compileSdk
    buildToolsVersion = DefaultConfig.buildToolsVersion
    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Modules
    implementation(project(Dependencies.Modules.shared))

    // Core
    implementation(Dependencies.Android.Core.coreKtx)
    implementation(Dependencies.Android.Core.appCompat)
    implementation(Dependencies.Android.Core.material)
    implementation(Dependencies.Android.Core.constraintLayout)
}
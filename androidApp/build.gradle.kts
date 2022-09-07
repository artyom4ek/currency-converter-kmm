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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
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
    implementation(Dependencies.Android.Core.activityKtx)

    with(Dependencies.Android.Compose) {
        implementation(runtime)
        implementation(compiler)
        implementation(activityCompose)
        implementation(ui)
        implementation(uiTooling)
        implementation(material)
        implementation(foundation)
    }
}
object Dependencies {
    object Modules {
        const val shared = ":shared"
    }

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val buildKonfig =
            "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Versions.buildKonfig}"
        const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
    }

    object Common {
        object Core {
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
            const val dateTime =
                "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.dateTime}"
            const val bigNum =
                "com.ionspin.kotlin:bignum:${Versions.bigNum}"
            const val multiplatformSettings =
                "com.russhwolf:multiplatform-settings-no-arg:${Versions.multiplatformSettings}"
        }

        object Koin {
            const val core = "io.insert-koin:koin-core:${Versions.koin}"
        }

        object Ktor {
            const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
            const val contentNegotiation =
                "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
            const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        }

        object SqlDelight {
            const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        }

        object Test {
            const val jUnit = "junit:junit:${Versions.jUnit}"
        }
    }

    object Android {
        object Core {
            const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
            const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
        }

        object Ktor {
            const val client = "io.ktor:ktor-client-android:${Versions.ktor}"
            const val okHttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        }

        object SqlDelight {
            const val androidDriver =
                "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        }

        object Compose {
            const val activityCompose =
                "androidx.activity:activity-compose:${Versions.activityCompose}"
            const val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
            const val runtime = "androidx.compose.runtime:runtime:${Versions.composeRuntime}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        }

        object Koin {
            const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
            const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
        }
    }

    object Ios {
        object Ktor {
            const val client = "io.ktor:ktor-client-darwin:${Versions.ktor}"
        }

        object SqlDelight {
            const val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        }
    }
}
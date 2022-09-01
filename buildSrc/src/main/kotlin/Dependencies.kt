object Dependencies {
    object Modules {
        const val shared = ":shared"
    }

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
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
            const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
            const val material = "com.google.android.material:material:${Versions.material}"
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
        }

        object Ktor {
            const val client = "io.ktor:ktor-client-android:${Versions.ktor}"
        }

        object SqlDelight {
            const val androidDriver =
                "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
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
object Dependencies {
    object Modules {
        const val shared = ":shared"
    }

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    }

    object Android {
        object Core {
            const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
            const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
            const val material = "com.google.android.material:material:${Versions.material}"
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        }
    }

    object Ios {

    }

    object Shared {
        const val jUnit = "junit:junit:${Versions.jUnit}"
    }
}
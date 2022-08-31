buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath(Dependencies.Plugins.kotlin)
        classpath(Dependencies.Plugins.gradle)
        classpath(Dependencies.Plugins.buildKonfig)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
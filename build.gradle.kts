buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
    dependencies {
        with(Dependencies.Plugins) {
            classpath(kotlin)
            classpath(gradle)
            classpath(buildKonfig)
            classpath(sqlDelight)
        }
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
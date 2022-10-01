buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        gradlePluginPortal()
    }
    dependencies {
        with(Dependencies.Plugins) {
            classpath(kotlin)
            classpath(gradle)
            classpath(buildKonfig)
            classpath(sqlDelight)
            classpath(resourcesGenerator)
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
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

/* https://github.com/gradle/gradle/issues/17236 */
gradle.taskGraph.whenReady {
    allTasks
        .filter { it.hasProperty("duplicatesStrategy") }
        .forEach {
            it.setProperty("duplicatesStrategy", "EXCLUDE")
        }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
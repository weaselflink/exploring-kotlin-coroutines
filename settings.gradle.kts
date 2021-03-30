@file:Suppress("LocalVariableName")

rootProject.name = "exploring-kotlin-coroutines"

pluginManagement {
    val kotlin_version: String by settings
    plugins {
        kotlin("jvm") version kotlin_version
        id("com.github.ben-manes.versions") version "0.38.0"
    }
}

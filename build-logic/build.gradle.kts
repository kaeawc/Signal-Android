import org.gradle.kotlin.dsl.extra

buildscript {
    val kotlinVersion by extra("2.0.21")

    repositories {
        google()
        mavenCentral()
        maven {
          url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:compose-compiler-gradle-plugin:$kotlinVersion")
    }
}

apply(from = "${rootDir}/../constants.gradle.kts")


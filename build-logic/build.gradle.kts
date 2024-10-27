import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        maven {
          url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath(libs.kgp)
        classpath(libs.compose.compiler)
        classpath(libs.ktlint.gradle)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        languageVersion.set(
            KotlinVersion.valueOf(
                "KOTLIN_${libs.versions.build.kotlin.language.get().replace(".", "_")}"))
        jvmTarget.set(JvmTarget.valueOf("JVM_${libs.versions.build.java.target.get()}"))
        freeCompilerArgs.addAll(
            listOf(
                "-opt-in=kotlin.time.ExperimentalTime,kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlin.ExperimentalUnsignedTypes",
                "-opt-in=kotlin.time.ExperimentalTime",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-Xcontext-receivers",
            ))
    }
}

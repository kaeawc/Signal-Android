import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  id("kotlin")
  id("groovy-gradle-plugin")
  id("org.jlleitschuh.gradle.ktlint")
}

group = "co.hinge.gradle"
version = "1.0.0-SNAPSHOT"

repositories {
  gradlePluginPortal()
  google()
  mavenCentral()
  maven {
    url = uri("https://plugins.gradle.org/m2/")
  }
}

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
  targetCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
}

tasks.withType<KotlinCompile> {
  compilerOptions {
    jvmTarget.set(JvmTarget.valueOf("JVM_${libs.versions.build.java.target.get()}"))
  }
}

dependencies {
  implementation(libs.kgp)
  implementation(libs.android.library)
  implementation(libs.android.application)
  implementation(project(":tools"))
  implementation(libs.ktlint.gradle)
  implementation(libs.compose.compiler)

  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

ktlint {
  filter {
    exclude { element ->
      element.file.path.contains("/build/generated-sources")
    }
  }
}

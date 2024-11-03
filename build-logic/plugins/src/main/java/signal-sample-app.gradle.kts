@file:Suppress("UnstableApiUsage")

import gradle.kotlin.dsl.accessors._9bb551205081e602fd4404de0d660bf4.implementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion

val libs = the<LibrariesForLibs>()

plugins {
  `version-catalog`
  id("com.android.application")
  id("kotlin-android")
  id("org.jlleitschuh.gradle.ktlint")
  id("org.jetbrains.kotlin.plugin.compose")
}

android {
  buildToolsVersion = libs.versions.build.android.buildTools.get()
  compileSdk = libs.versions.build.android.compileSdk.get().toInt()

  defaultConfig {
    versionCode = 1
    versionName = "1.0"

    minSdk = libs.versions.build.android.minSdk.get().toInt()
    targetSdk = libs.versions.build.android.targetSdk.get().toInt()
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
  }

  buildFeatures {
    compose = true
  }
}

dependencies {
  coreLibraryDesugaring(libs.android.tools.desugar)

  implementation(project(":core-util"))

  coreLibraryDesugaring(libs.android.tools.desugar)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.fragment.ktx)
  implementation(libs.androidx.annotation)
  implementation(libs.androidx.appcompat)
  implementation(libs.rxjava3.rxandroid)
  implementation(libs.rxjava3.rxjava)
  implementation(libs.rxjava3.rxkotlin)
  implementation(libs.material.material)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.kotlin.stdlib.jdk8)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.material)
  implementation(libs.androidx.compose.runtime)

  ktlintRuleset(libs.ktlint.twitter.compose)

  testImplementation(libs.junit)
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.android)
  testImplementation(libs.mockito.kotlin)
  testImplementation(libs.robolectric)
  testImplementation(libs.androidx.test.core)
  testImplementation(libs.androidx.test.core.ktx)
}

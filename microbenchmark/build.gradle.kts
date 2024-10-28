@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
  id("com.android.library")
  id("androidx.benchmark")
  id("org.jetbrains.kotlin.android")
  id("org.jlleitschuh.gradle.ktlint")
}

android {
  namespace = "org.signal.microbenchmark"
  compileSdk = libs.versions.build.android.compileSdk.get().toInt()

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
  }

  defaultConfig {
    minSdk = libs.versions.build.android.minSdk.get().toInt()
    targetSdk = libs.versions.build.android.targetSdk.get().toInt()

    testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
  }

  testBuildType = "release"
  buildTypes {
    debug {
      // Since isDebuggable can't be modified by gradle for library modules,
      // it must be done in a manifest - see src/androidTest/AndroidManifest.xml
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "benchmark-proguard-rules.pro")
    }
    release {
      isDefault = true
    }
  }
}

dependencies {
  coreLibraryDesugaring(libs.android.tools.desugar)
  lintChecks(project(":lintchecks"))

  implementation(project(":core-util"))

  // Base dependencies
  androidTestImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.benchmark.micro)

  // Dependencies of modules being tested
  androidTestImplementation(project(":libsignal-service"))
  androidTestImplementation(libs.libsignal.android)
}

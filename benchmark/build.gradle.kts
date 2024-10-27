@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
  `version-catalog`
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

android {
  namespace = "org.signal.benchmark"
  compileSdk = libs.versions.build.android.compileSdk.get().toInt()

    compileOptions {
      sourceCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
      targetCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
    }

    defaultConfig {
      minSdk = 28
      targetSdk = libs.versions.build.android.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        missingDimensionStrategy("environment", "prod")
        missingDimensionStrategy("distribution", "play")
    }

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks += listOf("perf", "debug")
        }
    }

    targetProjectPath = ":Signal-Android"
    experimentalProperties["android.experimental.self-instrumenting"] = true

    testOptions {
        managedDevices {
            devices {
                create("api31", ManagedVirtualDevice::class) {
                    device = "Pixel 6"
                    apiLevel = 31
                    systemImageSource = "aosp"
                    require64Bit = false
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso)
    implementation(libs.uiautomator)
    implementation(libs.androidx.benchmark.macro)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enabled = it.buildType == "benchmark"
    }
}

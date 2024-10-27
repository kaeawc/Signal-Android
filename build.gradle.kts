import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  dependencies {
    classpath(libs.agp)
    classpath(libs.kgp)
    classpath(libs.androidx.navigation.safe.args)
    classpath(libs.protobuf)
    classpath(libs.ktlint.gradle)
    classpath(libs.compose.compiler)
    classpath(libs.wire)
//    {
//      exclude(group = "com.squareup.wire", module = "wire-swift-generator")
//      exclude(group = "com.squareup.wire", module = "wire-grpc-client")
//      exclude(group = "com.squareup.wire", module = "wire-grpc-jvm")
//      exclude(group = "com.squareup.wire", module = "wire-grpc-server-generator")
//      exclude(group = "io.outfoxx", module = "swiftpoet")
//    }
    classpath(libs.androidx.benchmark)
    classpath(files("$rootDir/wire-handler/wire-handler-1.0.0.jar"))
    classpath(libs.ksp)
  }
}

plugins {
  `version-catalog`
  alias(libs.plugins.dependencyAnalysis)
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.application) apply false
}

tasks.withType<Wrapper> {
  distributionType = Wrapper.DistributionType.ALL
}

subprojects {

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

  if (JavaVersion.current().isJava8Compatible) {
    allprojects {
      tasks.withType<Javadoc> {
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
      }
    }
  }

  val skipQa = setOf("Signal-Android", "libsignal-service", "lintchecks", "benchmark", "core-util-jvm", "logging")

  if (project.name !in skipQa && !project.name.endsWith("-app")) {
    tasks.register("qa") {
      group = "Verification"
      description = "Quality Assurance. Run before pushing"
      dependsOn(
        // "clean",
        "testReleaseUnitTest",
        "lintRelease"
      )
    }
  }
}

tasks.register("buildQa") {
  group = "Verification"
  description = "Quality Assurance for build logic."
  dependsOn(
    gradle.includedBuild("build-logic").task(":tools:test"),
    gradle.includedBuild("build-logic").task(":tools:ktlintCheck"),
    gradle.includedBuild("build-logic").task(":plugins:ktlintCheck")
  )
}

tasks.register("qa") {
  group = "Verification"
  description = "Quality Assurance. Run before pushing."
  dependsOn(
//    "clean",
    "buildQa",
    ":Signal-Android:testPlayProdReleaseUnitTest",
    ":Signal-Android:lintPlayProdRelease",
    "Signal-Android:ktlintCheck",
    ":libsignal-service:test",
    ":libsignal-service:ktlintCheck",
    ":Signal-Android:assemblePlayProdRelease",
    ":Signal-Android:compilePlayProdInstrumentationAndroidTestSources",
    ":microbenchmark:compileReleaseAndroidTestSources",
    ":core-util-jvm:test",
    ":core-util-jvm:ktlintCheck"
  )
}

tasks.register("clean", Delete::class) {
  delete(layout.buildDirectory)
}

tasks.register("format") {
  group = "Formatting"
  description = "Runs the ktlint formatter on all sources in this project and included builds"
  dependsOn(
    gradle.includedBuild("build-logic").task(":plugins:ktlintFormat"),
    gradle.includedBuild("build-logic").task(":tools:ktlintFormat"),
    *subprojects.mapNotNull { tasks.findByPath(":${it.name}:ktlintFormat") }.toTypedArray()
  )
}

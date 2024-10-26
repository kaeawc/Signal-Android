import org.gradle.kotlin.dsl.extra

plugins {
  `kotlin-dsl`
  `version-catalog`
  alias(libs.kgp)
  alias(libs.plugins.ktlint)
  id("groovy-gradle-plugin")
}

val signalJavaVersion: JavaVersion by rootProject.extra
val signalKotlinJvmTarget: String by rootProject.extra

java {
  sourceCompatibility = signalJavaVersion
  targetCompatibility = signalJavaVersion
}

kotlinDslPluginOptions {
  jvmTarget.set(signalKotlinJvmTarget)
}

dependencies {
  implementation(libs.kgp)
  implementation(libs.plugins.android.library)
  implementation(libs.plugins.android.application)
  implementation(project(":tools"))
  implementation(libs.plugins.ktlint)

  // These allow us to reference the dependency catalog inside of our compiled plugins
  // TODO: fix build logic class path with workaround
//  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
//  implementation(files(testLibs.javaClass.superclass.protectionDomain.codeSource.location))
}

ktlint {
  filter {
    exclude { element ->
      element.file.path.contains("/build/generated-sources")
    }
  }
}

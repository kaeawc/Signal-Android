plugins {
  id("org.jetbrains.kotlin.jvm")
  id("java-library")
  id("org.jlleitschuh.gradle.ktlint")
}

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
  targetCompatibility = JavaVersion.toVersion(libs.versions.build.java.target.get())
}

// NOTE: For now, in order to run ktlint on this project, you have to manually run ./gradlew :build-logic:tools:ktlintFormat
//       Gotta figure out how to get it auto-included in the normal ./gradlew ktlintFormat
ktlint {
  version.set(libs.versions.ktlint.get())
}

dependencies {
  implementation(gradleApi())

  implementation(libs.dnsjava)
  testImplementation(libs.junit)
  testImplementation(libs.mockk)
}

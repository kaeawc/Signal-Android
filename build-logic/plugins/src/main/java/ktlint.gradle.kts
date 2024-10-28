import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
  id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
  version.set(libs.versions.ktlint.get())
}

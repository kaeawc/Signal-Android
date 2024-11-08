plugins {
  id("signal-library")
}

android {
  namespace = "org.signal.core.ui"
}

dependencies {
  lintChecks(project(":lintchecks"))

  platform(libs.compose.bom).let { composeBom ->
    api(composeBom)
    androidTestApi(composeBom)
  }

  api(libs.compose.material)
  api(libs.compose.ui.tooling.preview)
  debugApi(libs.compose.ui.tooling)
}

plugins {
  id("signal-library")
}

android {
  namespace = "org.signal.devicetransfer"
}

dependencies {
  implementation(project(":core-util"))
  implementation(libs.libsignal.android)
  api(libs.greenrobot.eventbus)

  testImplementation(libs.robolectric) {
    exclude(group = "com.google.protobuf", module = "protobuf-java")
  }
  testImplementation(libs.hamcrest.hamcrest)

  testImplementation(testFixtures(project(":libsignal-service")))
}

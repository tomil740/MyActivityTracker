plugins {
    alias(libs.plugins.myactivitytracker.android.library)
    alias(libs.plugins.myactivitytracker.jvm.ktor)
}

android {
    namespace = "com.tomiappdevelopment.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}
plugins {
    alias(libs.plugins.myactivitytracker.android.library)
    alias(libs.plugins.myactivitytracker.jvm.ktor)
}

android {
    namespace = "com.tomiappdevelopment.auth.data"
}

dependencies {

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(libs.bundles.koin)
}
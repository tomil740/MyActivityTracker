plugins {
    alias(libs.plugins.myactivitytracker.android.library)
    alias(libs.plugins.myactivitytracker.jvm.ktor)
}

android {
    namespace = "com.tomiappdevelopment.core.data"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.database)
}
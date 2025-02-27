plugins {
    alias(libs.plugins.myactivitytracker.android.feature.ui)
}

android {
    namespace = "com.tomiappdevelopment.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}
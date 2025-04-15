plugins {
    alias(libs.plugins.myactivitytracker.android.feature.ui)
}

android {
    namespace = "com.tomiappdevelopment.goalstracker.presentation"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.goalstracker.domain)
}
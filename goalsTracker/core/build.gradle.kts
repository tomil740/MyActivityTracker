plugins {
    alias(libs.plugins.myactivitytracker.android.feature.ui)
}

android {
    namespace = "com.tomiappdevelopment.goalstracker.core"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.work)
    implementation(libs.koin.android.workmanager)
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.run.domain)
    implementation(libs.bundles.koin)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
}
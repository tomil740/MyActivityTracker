plugins {
    alias(libs.plugins.myactivitytracker.android.library)
    alias(libs.plugins.myactivitytracker.android.room)
}

    android {
    namespace = "com.tomiappdevelopment.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
    implementation(libs.bundles.koin)

}
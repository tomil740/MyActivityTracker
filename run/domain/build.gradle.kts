plugins {
    alias(libs.plugins.myactivitytracker.jvm.library)
}
dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(project(":core:domain"))
}
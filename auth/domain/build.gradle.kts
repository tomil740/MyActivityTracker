plugins {
    alias(libs.plugins.myactivitytracker.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}
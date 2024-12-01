plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    testImplementation(libs.junit)

    compileOnly(libs.okhttp)
    testImplementation(libs.okhttp)
}
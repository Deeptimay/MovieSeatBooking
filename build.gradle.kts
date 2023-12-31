// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // Check that you have the following line (if not, add it):
        google()  // Google's Maven repository
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.0.0")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}
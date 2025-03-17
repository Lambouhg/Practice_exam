// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    id("com.google.gms.google-services") version "4.4.2" apply false
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory.get()) // Sửa lỗi deprecated
}

buildscript {
    repositories {
        google()
        mavenCentral()

    }
    dependencies {

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")  // Sử dụng phiên bản Kotlin 1.8.22
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.android.tools.build:gradle:8.2.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
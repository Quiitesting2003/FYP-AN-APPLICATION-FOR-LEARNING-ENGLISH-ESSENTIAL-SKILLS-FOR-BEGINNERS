plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.elearning"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.elearning"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
    buildToolsVersion = "34.0.0"

}

dependencies {
    // Firebase BOM for managing compatible versions
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-config:22.0.1")

    // ML Kit dependencies
    implementation("com.google.mlkit:language-id:17.0.1")
    implementation("com.google.firebase:firebase-ml-modeldownloader")


    // TensorFlow Lite
    implementation("org.tensorflow:tensorflow-lite:2.11.0")

    // Google Play Services Tasks
    implementation("com.google.android.gms:play-services-tasks:18.1.0")

    // AndroidX and Material Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.nuvei.nuvei_sdk_android"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nuvei.nuvei_sdk_android"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(project(":NUVEI_SDK"))
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    implementation(libs.gson)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
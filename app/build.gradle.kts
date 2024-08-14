plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.plugin.serialization") version ("1.9.22")

}

android {
    namespace = "com.sci.coffeeandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sci.coffeeandroid"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        getByName("debug") {
            keyAlias = "coffee-android"
            keyPassword = "android"
            storeFile = rootProject.file("keystore/debug-keystore.jks")
            storePassword = "android"
        }
    }

    buildTypes {
        debug{
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.bundles.koin.bundle)
    implementation(libs.bundles.ktor.bundle)
    implementation(libs.glide.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.mmkv.android)
    implementation(libs.kotlinx.serialization)

    implementation(libs.kotlinx.coroutines.android.v139)

    implementation(libs.androidx.credentials)
    implementation (libs.googleid.v122)

    implementation ("com.facebook.android:facebook-android-sdk:17.0.1")

}
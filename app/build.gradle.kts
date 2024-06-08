import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

fun getPropKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir)
        .getProperty(propertyKey)
}

android {
    namespace = "com.apx8.mongoose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apx8.mongoose"
        minSdk = 31
        targetSdk = 34
        versionCode = 10000
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", getPropKey("API_KEY"))
        buildConfigField("String", "ADMOB_DEBUG_KEY", getPropKey("ADMOB_DEBUG_KEY"))
        buildConfigField("String", "ADMOB_RELEASE_KEY", getPropKey("ADMOB_RELEASE_KEY"))
    }

    signingConfigs {
        create("release") {
            storeFile = file("../../mongoose_keystore/mongoose_keystore.jks")
            storePassword = System.getenv("MONGOOSE_KEYSTORE_PW")
            keyAlias = "mongoose_alias"
            keyPassword = System.getenv("MONGOOSE_KEY_PW")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /**
     * @info
     * lifecycle 관련 모듈, 2.7.0 버전업시, 앱 죽음
     * (CompositionLocal LocalLifecycleOwner not present)
     */

    /* Android*/
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    /* Test*/
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    /* Hilt*/
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.46")

    /* Retrofit*/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")

    /* Joda Time*/
    implementation("net.danlew:android.joda:2.12.5")

    /* AdMob*/
    implementation("com.google.android.gms:play-services-ads:23.1.0")

    /* Preference*/
    implementation("androidx.preference:preference-ktx:1.2.1")

    /* Firebase*/
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
}
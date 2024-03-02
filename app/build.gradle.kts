plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.company.khomasi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.company.khomasi"
        minSdk = 21
        //noinspection EditedTargetSdkVersion
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.company.khomasi.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}



dependencies {


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Splash Api
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //Compose Foundation
    implementation ("androidx.compose.foundation:foundation:1.6.1")

    //Accompanist
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    // Compose dependencies
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")

    val composeVersion = "1.6.1"
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${composeVersion}")
    debugImplementation("androidx.compose.ui:ui-tooling:${composeVersion}")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    //room
    val room = "2.6.1"
    implementation("androidx.room:room-runtime:${room}")
    implementation("androidx.room:room-ktx:${room}")
    ksp("androidx.room:room-compiler:${room}")

    //retrofit
    val retrofit = "2.9.0"
    val okHttp = "4.12.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofit}")
    implementation("com.squareup.okhttp3:okhttp:${okHttp}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okHttp}")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //RatingBar
    implementation ("com.github.a914-gowtham:compose-ratingbar:1.3.4")

    // Local unit tests
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("com.google.truth:truth:1.4.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:${okHttp}")
    testImplementation("com.squareup.okhttp3:okhttp:${okHttp}")

    // Instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.50")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.50")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("com.google.truth:truth:1.4.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${okHttp}")
    androidTestImplementation("com.squareup.okhttp3:okhttp:${okHttp}")


}
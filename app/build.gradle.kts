plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.safe.args.kotlin)
    id("kotlin-parcelize")
}

android {
    namespace  = "com.mod6.ae3_abpro1_loginapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mod6.ae3_abpro1_loginapp"
        minSdk        = 29
        targetSdk     = 34
        versionCode   = 1
        versionName   = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    // -------------------------------------------------------
    // IMPLEMENTATION (Core, UI, Architecture, Network)
    // -------------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splashscreen)

    // Arquitectura (Lifecycle y Navigation)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Network & Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // Imaging
    implementation(libs.glide)

    // Firebase (si aplica al módulo de aplicación)
    implementation(libs.firebase.crashlytics.buildtools)

    // -------------------------------------------------------
    // UNIT TESTS (JVM) - SIMPLIFICADO Y CONSOLIDADO
    // -------------------------------------------------------
    testImplementation(libs.junit)
    testImplementation(libs.arch.core.testing) // Para probar LiveData
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockwebserver) // Para probar la capa de red
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.21") // o la versió

    // MockK (Reemplaza a Mockito para 'coEvery/coVerify' sin conflictos)
    testImplementation("io.mockk:mockk:1.13.11") // Usaremos una versión de MockK compatible
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk)


    // -------------------------------------------------------
    // ANDROID INSTRUMENTED TESTS
    // -------------------------------------------------------
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
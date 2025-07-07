plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.mindorks.framework.mvvm"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.mindorks.framework.mvvm"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://www.mocky.io/v2\"")
            buildConfigField("String", "API_KEY", "\"ABCXYZ123TEST\"")
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://www.mocky.io/v2\"")
            buildConfigField("String", "API_KEY", "\"ABCXYZ123TEST\"")
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
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    dataBinding {
        enable = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Material Design
    implementation(libs.material)

    // Architecture Components
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Networking
    implementation(libs.bundles.networking)
    ksp(libs.moshi.codegen)

    // Image Loading
    implementation(libs.glide.core)
    ksp(libs.glide.compiler)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // UI Components
    implementation(libs.androidx.viewpager2)

    // Utilities
    implementation(libs.timber)
    implementation(libs.gson)

    // Debug Tools (Debug builds only)
    debugImplementation(libs.flipper.core)
    debugImplementation(libs.flipper.network)
    debugImplementation(libs.leakcanary)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.espresso.intents)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
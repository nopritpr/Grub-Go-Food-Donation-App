plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services) // Firebase services
}

android {
    namespace = "com.example.grubgo"
    compileSdk = 35 // Ensure your SDK tools and devices support this version

    defaultConfig {
        applicationId = "com.example.grubgo"
        minSdk = 24 // Minimum supported Android version
        targetSdk = 35
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

            kotlinOptions {
                jvmTarget = "1.8"
            }

            buildFeatures {
                viewBinding = true // Enable ViewBinding
            }
        }


        dependencies {
            // Core Android Libraries
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.material) // Material Components
            implementation(libs.androidx.activity)
            implementation(libs.androidx.constraintlayout) // ConstraintLayout

            // Firebase Authentication
            implementation(platform("com.google.firebase:firebase-bom:33.7.0")) // Firebase BOM
            implementation("com.google.firebase:firebase-auth-ktx") // Firebase Auth

            // Google Play Services for Google Authentication
            implementation("com.google.android.gms:play-services-auth:20.6.0")

            // Mapbox SDK
            implementation("com.mapbox.maps:android:11.9.0") // Latest Mapbox SDK

            // Glide for image loading
            implementation("com.github.bumptech.glide:glide:4.15.1")
            annotationProcessor("com.github.bumptech.glide:compiler:4.15.1") // Ensure annotation processor is correctly configured

            // Testing Libraries
            testImplementation(libs.junit)
            androidTestImplementation(libs.androidx.junit)
            androidTestImplementation(libs.androidx.espresso.core)
        }


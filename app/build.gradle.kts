plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //Add Plugins KSP
    alias(libs.plugins.devtoolsKsp)
//    id ("com.google.devtools.ksp")
    id ("kotlin-parcelize")

}

android {
    namespace = "com.bangkit.submissionAwal"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.bangkit.submissionAwal"
        minSdk = 29
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
        debug {
            //BASE URL
            buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
            //API KEY
            buildConfigField("String", "API_KEY", "\"token \"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Library untuk networking
    implementation(libs.asyncHttp)
    //retrofit
    implementation(libs.retrofit)
    implementation (libs.retrofitConverter)
    //glide
    implementation(libs.glide)
    //interceptor
    implementation(libs.interceptor)
    // ViewModel & Live Data
//    implementation(libs.viewmodel)
    implementation(libs.livedata)
    //ViewPager
    implementation(libs.viewPager)
    //room database
    implementation(libs.roomRuntimeDatabase)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.room.ktx)

}
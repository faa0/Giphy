import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.AGP.application)
    kotlin(Plugins.Kotlin.android)
    kotlin(Plugins.Kotlin.kapt)

    // Hilt
    id(Plugins.Hilt.plugin)
    //Safe Args
    id(Plugins.SafeArgs.plugin)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = "com.fara.giphy"
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {

        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "key.properties")))
        }

        getByName(AndroidConfig.release) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.giphy.com/\"")

            buildConfigField("String", "GIPHY_KEY", prop.getProperty("GIPHY_KEY"))
        }

        getByName(AndroidConfig.debug) {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"https://api.giphy.com/\"")

            buildConfigField("String", "GIPHY_KEY", prop.getProperty("GIPHY_KEY"))
        }
    }
    compileOptions {
        sourceCompatibility = Options.compileOptions
        targetCompatibility = Options.compileOptions
    }
    kotlinOptions {
        jvmTarget = Options.kotlinOptions
    }

    // View Binding
    buildFeatures.viewBinding = true
}

dependencies {

    // Kotlin
    implementation(Libraries.Coroutines.android)

    // UI Components
    implementation(Libraries.UIComponents.material)
    implementation(Libraries.UIComponents.constraintLayout)
    implementation(Libraries.UIComponents.swipeRefreshLayout)

    // Core
    implementation(Libraries.Core.core)

    // Activity
    implementation(Libraries.Activity.activity)

    // Fragment
    implementation(Libraries.Fragment.fragment)

    // Lifecycle
    implementation(Libraries.Lifecycle.viewModel)
    implementation(Libraries.Lifecycle.runtime)

    // Navigation
    implementation(Libraries.Navigation.fragment)
    implementation(Libraries.Navigation.ui)

    // Hilt
    implementation(Libraries.Hilt.android)
    kapt(Libraries.Hilt.compiler)

    // Retrofit 2
    implementation(Libraries.Retrofit.retrofit)
    implementation(Libraries.Retrofit.converterGson)

    // OkHttp
    implementation(Libraries.OkHttp.bom)
    implementation(Libraries.OkHttp.okHttp)
    implementation(Libraries.OkHttp.loggingInterceptor)

    // Room
    implementation(Libraries.Room.room)
    implementation(Libraries.Room.runtime)
    implementation(Libraries.Room.paging)
    kapt(Libraries.Room.compiler)

    // Paging 3
    implementation(Libraries.Paging.runtime)

    // Glide
    implementation(Libraries.Glide.glide)
    kapt(Libraries.Glide.compiler)
}

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.google.protobuf.gradle.*

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.HILT)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.PROTO_DATASTORE)
}

val addressServiceKey: String = gradleLocalProperties(rootDir).getProperty("address_service_key")
val forecastServiceKey: String = gradleLocalProperties(rootDir).getProperty("forecast_api_key")

android {
    namespace = "com.jymun.usanchaengyeo"
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.jymun.usanchaengyeo"
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            buildConfigField("String", "address_service_key", addressServiceKey)
            buildConfigField("String", "forecast_api_key", forecastServiceKey)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)

    // Testing
    testImplementation(Testing.JUNIT4)
    androidTestImplementation(Testing.ANDROID_JUNIT)
    androidTestImplementation(Testing.ESPRESSO_CORE)

    // Hilt
    implementation(Dependencies.Hilt)
    kapt(Dependencies.HILT_KAPT)

    // Coroutine
    implementation(Dependencies.COROUTINE)

    // Lifecycle
    implementation(Dependencies.VIEWMODEL)
    implementation(Dependencies.LIVEDATA)
    implementation(Dependencies.ACTIVITY_KTX)
    implementation(Dependencies.FRAGMENT_KTX)

    // WeatherView
    implementation(Dependencies.WEATHER_VIEW)

    // Location Service
    implementation(Dependencies.LOCATION_SERVICE)

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.GSON_CONVERTER)
    implementation(Dependencies.OKHTTP3)
    implementation(Dependencies.OKHTTP3_INTERCEPTOR)

    // Navigation
    implementation(Dependencies.NAVIGATION_UI_KTX)
    implementation(Dependencies.NAVIGATION_FRAGMENT_KTX)

    // Room
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)

    // RecyclerView Swipe Helper
    implementation(Dependencies.SWIPE_HELPER)

    // Spin Kit
    implementation(Dependencies.SPIN_KIT)

    // Proto DataStore
    implementation(Dependencies.DATASTORE)
    implementation(Dependencies.PROTO_DATASTORE)
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = Dependencies.PROTOC
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
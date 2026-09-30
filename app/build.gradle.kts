import java.util.Base64
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

fun getEnvValue(key: String): String? {
    val envData = System.getenv(key)
    if (envData != null) return envData

    val properties = Properties()
    val propertiesFile = project.rootProject.file("local.properties")
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use {
            properties.load(it)
        }
    }
    return properties.getProperty(key)
}

fun getKeyStoreFile(base64String: String): File {
    val keystoreDir = File(rootDir, "keystore")
    val keystoreFile = File(keystoreDir, "release-key.jks")
    keystoreDir.mkdirs()
    val decoded = Base64.getDecoder().decode(base64String)
    keystoreFile.writeBytes(decoded)
    return keystoreFile
}

val versionMajor = 2
val versionMinor = 0
val versionPatch = 0

android {
    namespace = "baltiapps.training.lifecycleplayground"
    compileSdk = 37

    defaultConfig {
        applicationId = "baltiapps.training.lifecycleplayground"
        minSdk = 21
        targetSdk = 37
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = getKeyStoreFile(getEnvValue("KEYSTORE_BASE64") ?: "")
            storePassword = getEnvValue("KEYSTORE_PASSWORD")
            keyAlias = getEnvValue("KEY_ALIAS")
            keyPassword = getEnvValue("KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(25)
}

base {
    archivesName.set("ActivityPlayground-v${versionMajor}.${versionMinor}.${versionPatch}")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.accompanist.adaptive)
    implementation(libs.androidx.navigation.compose)
}
plugins {
    id("com.android.application")
}

android {
    namespace = "br.com.renancsdev.hinovaoficinas"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.renancsdev.hinovaoficinas"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    dataBinding {
        enable = true
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
}

dependencies {

    val stdlibVersion = "1.9.22"
    val retrofit2Version = "2.9.0"
    val okhttp3Version = "4.12.0"
    val gsonVersion = "2.10.1"
    val koushikduttaVersion= "3.1.0"
    val glideVersion = "4.16.0"
    val picassoVersion = "2.71828"
    val jacksonAnnot = "2.16.1"
    val easypermVers = "3.0.0"
    val gmsVersion = "21.1.0"
    val commonsIoVersion = ":2.15.1"

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    // Gson
    implementation("com.google.code.gson:gson:$gsonVersion")

    // jackson-annotations
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnot")

    //koushikdutta ion
    implementation("com.koushikdutta.ion:ion:$koushikduttaVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //Imagem
    implementation("com.squareup.picasso:picasso:$picassoVersion")

    // Permissoes
    implementation("pub.devrel:easypermissions:$easypermVers")

    implementation("commons-io:commons-io:2.15.1")

    //Google GPS
    implementation("com.google.android.gms:play-services-location:$gmsVersion")

    //fix Android studio
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$stdlibVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$stdlibVersion")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
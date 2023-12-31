plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight").version("2.0.0")
}


kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {

        val voyagerVersion = extra["voyager.version"] as String

        //val sqlDelightVersion = extra["sqlDelight.version"] as String

        //val kotlinxDateTimeVersion = extra["kotlinx.datetime.version"] as String

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

                //implementation("app.cash.sqldelight:2.0.0")

                //implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
               //implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion")
                //implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion")

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                //implementation("com.squareup.sqldelight:android-driver:1.5.5")
                implementation("app.cash.sqldelight:android-driver:2.0.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies{
                //implementation("com.squareup.sqldelight:native-driver:1.5.5")
                implementation("app.cash.sqldelight:native-driver:2.0.0")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
dependencies {
    implementation("androidx.core:core-ktx:+")
}

sqldelight {
    database("MyDatabase") {
        packageName = "com.myapplication.common.database"
        sourceFolders = listOf("sqldelight")

    }
}

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "net.projecttl"
version = "1.2.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "sonatype-oss-snapshots"
        }
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("net.kyori:adventure-api:4.7.0")
        implementation("net.projecttl:InventoryGUI-api:4.1.1")
        compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
        compileOnly("mysql:mysql-connector-java:8.0.26") // MySQL Adapter
    }
}
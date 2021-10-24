plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.dokka") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "net.projecttl"
version = "2.0.0"

allprojects {
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }

    repositories {
        mavenCentral()
    }
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://m2.dv8tion.net/releases")
        maven("https://jitpack.io")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("net.dv8tion:JDA:4.3.0_277")
        implementation("net.kyori:adventure-api:4.9.2")
        implementation("net.projecttl:InventoryGUI-api:4.1.8")
        implementation("net.md-5:bungeecord-api:1.17-R0.1-SNAPSHOT")

        compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
        compileOnly("mysql:mysql-connector-java:8.0.26") // MySQL Adapter
    }
}
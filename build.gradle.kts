plugins {
    kotlin("jvm") version "1.7.0"
    id("org.jetbrains.dokka") version "1.6.21"
}

group = property("group")!!
version = property("version")!!

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    repositories {
        mavenCentral()
    }
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        implementation(kotlin("stdlib"))
        compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
        compileOnly("mysql:mysql-connector-java:8.0.26")
    }
}

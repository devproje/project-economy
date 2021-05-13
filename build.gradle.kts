plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"

    `maven-publish`
}

group = properties["pluginGroup"]!!
version = properties["pluginVersion"]!!

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots"
    }
}

dependencies {
    implementation("org.codehaus.groovy:groovy-all:3.0.5")
    implementation("net.kyori:adventure-api:4.7.0")

    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"

        sourceCompatibility = "16"
        targetCompatibility = "16"
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifact(tasks["sourceJar"])
            from(components["java"])
        }
    }
}
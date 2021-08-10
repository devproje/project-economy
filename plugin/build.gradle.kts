plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":${rootProject.name}-api"))
}

tasks {
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
        archiveBaseName.set(rootProject.name)
        archiveVersion.set("")
        archiveClassifier.set("")
    }
}
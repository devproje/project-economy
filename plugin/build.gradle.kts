group = rootProject.group
version = "1.2.1"

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
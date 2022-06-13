group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":${rootProject.name}-core"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
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
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(rootProject.version.toString())
        archiveClassifier.set("")
    }
}
group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":${rootProject.name}-core"))
    implementation("io.github.monun:kommand-api:2.12.0")
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

    create<Jar>("paperJar") {
        from(sourceSets["main"].output)
        archiveBaseName.set(rootProject.name)
        archiveVersion.set(project.version.toString())
    }
}
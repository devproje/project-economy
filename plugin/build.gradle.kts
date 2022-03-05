group = rootProject.group
version = rootProject.version

val plugins = File("C:\\Users\\MineStar\\Desktop\\MC Server folder\\MCserver 1.18.2 - MineFarm\\plugins")

dependencies {
    implementation("org.apache.commons:commons-io:1.3.2")
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

        doLast {
            // jar file copy
            copy {
                from(archiveFile)
                into(if (File(plugins, archiveFileName.get()).exists()) plugins else plugins)
            }
        }

    }
}
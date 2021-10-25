# project-economy
This is minecraft plugin MySQL money system.

## Command List
This is plugin command /economy or /money
- Show account balance
```mclang
/money account
```

- Send money
```mclang
/money send <another_player>
```

## How to use API

* Maven
```xml
<repositories>
    <repository>
        <releases>
            <enabled>true</enabled>
        </releases>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
</repositories>

<dependency>
  <groupId>net.projecttl</groupId>
  <artifactId>project-economy-api</artifactId>
  <version>Tag</version>
</dependency>
```
* Groovy Gradle DSL
```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation 'net.projecttl:project-economy-api:VERSION'
}
```

* Kotlin Gradle DSL
```kotlin
repositories {
  mavenCentral()
}

dependencies {
  implementation("net.projecttl:project-economy-api:VERSION")
}
```

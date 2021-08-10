# PBalance
This is minecraft plugin money system with MySQL.

## How to build
If you want to build this plugin please follow this command.

```sh
git clone https://github.com/ProjectTL12345/PEconomy.git
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
  <artifactId>PBalance-api</artifactId>
  <version>Tag</version>
</dependency>
```
* Groovy Gradle DSL
```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation 'net.projecttl:PBalance-api:VERSION'
}
```

* Kotlin Gradle DSL
```kotlin
repositories {
  mavenCentral()
}

dependencies {
  compileOnly("net.projecttl:PBalance-api:VERSION")
}
```

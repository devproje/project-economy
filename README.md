# PBalance
This is minecraft plugin MySQL money system.

## Command List
This is plugin command /pbalance or /money
- Show account balance
```mclang
/money balance
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

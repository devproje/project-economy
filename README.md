# project-economy
This is minecraft plugin MySQL money system.

## Command List
- Check account balance
```mclang
/money
```

- Add player's money
```mclang
/money add <another_player> <amount>
```

- Subtract player's money
```mclang
/money drop <another_player> <amount>
```

- Query accounts
```mclang
/money query
```

- Send money
```mclang
/send <another_player> <amount>
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

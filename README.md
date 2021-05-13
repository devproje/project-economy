# PEconomy
This is minecraft plugin money system.

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
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.projecttl12345</groupId>
  <artifactId>PEconomy</artifactId>
  <version>Tag</version>
</dependency>
```
* Groovy Gradle DSL
```groovy
repositories {
  ...
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.projecttl12345:PEconomy:VERSION'
}
```

* Kotlin Gradle DSL
```kotlin
repositories {
  ...
  maven("https://jitpack.io")
}

dependencies {
  compileOnly("com.github.projecttl12345:PEconomy:VERSION")
}
```

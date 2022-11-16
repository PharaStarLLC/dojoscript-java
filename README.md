# DojoScript

```txt
▓█████▄  ▒█████   ▄▄▄██▀▀▀▒█████    ██████  ▄████▄   ██▀███   ██▓ ██▓███  ▄▄▄█████▓
▒██▀ ██▌▒██▒  ██▒   ▒██  ▒██▒  ██▒▒██    ▒ ▒██▀ ▀█  ▓██ ▒ ██▒▓██▒▓██░  ██▒▓  ██▒ ▓▒
░██   █▌▒██░  ██▒   ░██  ▒██░  ██▒░ ▓██▄   ▒▓█    ▄ ▓██ ░▄█ ▒▒██▒▓██░ ██▓▒▒ ▓██░ ▒░
░▓█▄   ▌▒██   ██░▓██▄██▓ ▒██   ██░  ▒   ██▒▒▓▓▄ ▄██▒▒██▀▀█▄  ░██░▒██▄█▓▒ ▒░ ▓██▓ ░
░▒████▓ ░ ████▓▒░ ▓███▒  ░ ████▓▒░▒██████▒▒▒ ▓███▀ ░░██▓ ▒██▒░██░▒██▒ ░  ░  ▒██▒ ░
 ▒▒▓  ▒ ░ ▒░▒░▒░  ▒▓▒▒░  ░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░░ ░▒ ▒  ░░ ▒▓ ░▒▓░░▓  ▒▓▒░ ░  ░  ▒ ░░
 ░ ▒  ▒   ░ ▒ ▒░  ▒ ░▒░    ░ ▒ ▒░ ░ ░▒  ░ ░  ░  ▒     ░▒ ░ ▒░ ▒ ░░▒ ░         ░
 ░ ░  ░ ░ ░ ░ ▒   ░ ░ ░  ░ ░ ░ ▒  ░  ░  ░  ░          ░░   ░  ▒ ░░░         ░
   ░        ░ ░   ░   ░      ░ ░        ░  ░ ░         ░      ░
 ░                                         ░

DojoScript - Developed with ♥ and Published by digitaldojo.tech
```

Fast text formatting language for Java and Javascript. Lightweight and No Dependencies.

---

## [Java Usage And Examples](./dojoscript/README.md)

--- 

### ADD REPO:

```groovy
// Repo - Groovy
repositories {
    maven {
        name = "digitaldojo-repo"
        url = "https://repo.digitaldojo.tech/repository/maven-snapshots/"
    }
}
```

```kt
// Repo - Kotlin DSL
repositories {
    maven {
        url = uri("https://repo.digitaldojo.tech/repository/maven-snapshots/")
    }

    // OR
    maven("https://repo.digitaldojo.tech/repository/maven-snapshots/")
}
```

```xml

<repositories>
    <repository>
        <id>digitaldojo-repo</id>
        <url>https://repo.digitaldojo.tech/repository/maven-snapshots/</url>
    </repository>
</repositories>
```

---

### ADD DEPENDENCY:

```groovy
// Dependency - Groovy
implementation("tech.digitaldojo.dojoscript:dojoscript:1.0-SNAPSHOT")
```

```kt
// Dependency - Kotlin DSL
implementation("tech.digitaldojo.dojoscript:dojoscript:1.0-SNAPSHOT")
```

```xml
<!-- Dependency - Maven -->
<dependencies>
    <dependency>
        <groupId>tech.digitaldojo.dojoscript</groupId>
        <artifactId>dojoscript</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

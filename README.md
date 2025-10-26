# Spring Boot Demo Application

Eine einfache Spring Boot Web-Anwendung mit CI/CD-Pipeline und Docker-Integration.

## 📋 Projektübersicht

Dieses Projekt ist eine Demo-Anwendung, die folgende Technologien und Best Practices demonstriert:

- **Spring Boot 3.5.7** - Modernes Java-Framework für Web-Anwendungen
- **Java 17** - LTS-Version mit moderner Java-Syntax
- **Maven** - Build-Management und Dependency-Management
- **JUnit 5** - Unit Testing Framework
- **Jib** - Containerisierung ohne Docker-Daemon
- **GitHub Actions** - Automatisierte CI/CD-Pipeline
- **GitHub Packages** - Container Registry für Docker-Images

## 🚀 Features

- REST-API mit zwei Endpoints:
  - `GET /` - Begrüßungsnachricht
  - `GET /something` - Status-Endpoint
- Automatisierte Tests mit JUnit 5
- Docker-Container-Build mit Jib
- Vollautomatische CI/CD-Pipeline

## 🏗️ Projektstruktur

```
.
├── .github/
│   └── workflows/
│       └── main.yml          # CI/CD Pipeline Definition
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cd/pipeline/demo/
│   │   │       ├── DemoApplication.java      # Spring Boot Main-Klasse
│   │   │       └── HelloController.java      # REST Controller
│   │   └── resources/
│   │       └── application.properties        # Anwendungskonfiguration
│   └── test/
│       └── java/
│           └── cd/pipeline/demo/
│               ├── DemoApplicationTests.java      # Context Load Test
│               └── HelloControllerTest.java       # Controller Tests
├── Dockerfile                # Docker Build (optional, Jib wird bevorzugt)
├── pom.xml                  # Maven Projekt-Konfiguration
├── JIB-USAGE.md            # Jib Dokumentation
└── README.md               # Diese Datei
```

## 🛠️ Voraussetzungen

- **Java 17** oder höher
- **Maven 3.6+**
- **Docker** (optional, nur für lokale Docker-Builds)
- **Git** für Version Control

## 📦 Installation und Build

### Lokaler Build

```bash
# Projekt klonen
git clone <repository-url>
cd spring-boot-webapp-uebung

# Kompilieren
mvn clean compile

# Tests ausführen
mvn test

# JAR-Datei erstellen
mvn package
```

### Anwendung lokal starten

```bash
# Mit Maven
mvn spring-boot:run

# Oder als JAR
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Die Anwendung läuft dann auf: http://localhost:8080

## 🐳 Docker

### Mit Jib (empfohlen)

```bash
# Docker-Image in lokalen Docker-Daemon bauen
mvn compile jib:dockerBuild

# Direkt zu Registry pushen (ohne Docker)
mvn compile jib:build

# Als Tar-Datei exportieren
mvn compile jib:buildTar
```

### Container starten

```bash
docker run -p 8080:8080 cd/pipeline/demo:latest
```

Weitere Details siehe [JIB-USAGE.md](JIB-USAGE.md)

## 🔄 CI/CD Pipeline

Das Projekt enthält eine vollautomatische GitHub Actions Pipeline (`.github/workflows/main.yml`), die bei jedem Push auf den `master`-Branch ausgeführt wird.

### Pipeline-Schritte:

#### Job 1: Build and Test
1. ✅ Code auschecken
2. ✅ JDK 17 einrichten
3. ✅ Projekt kompilieren
4. ✅ Unit Tests ausführen
5. ✅ JAR-Datei paketieren
6. ✅ Build-Artefakte hochladen

#### Job 2: Docker Build & Push
(Wird nur ausgeführt, wenn Job 1 erfolgreich war)

1. ✅ Code auschecken
2. ✅ JDK 17 einrichten
3. ✅ Bei GitHub Container Registry anmelden
4. ✅ Docker-Image mit Jib bauen und pushen
5. ✅ Image-Details ausgeben

### Docker-Image abrufen

Nach erfolgreichem Pipeline-Durchlauf ist das Image verfügbar unter:

```bash
# Latest Version
docker pull ghcr.io/<github-username>/demo:latest

# Spezifische Version (Commit SHA)
docker pull ghcr.io/<github-username>/demo:<commit-sha>
```

## 🧪 Tests

Das Projekt enthält zwei Testklassen:

- **DemoApplicationTests** - Überprüft, ob der Spring Context korrekt lädt
- **HelloControllerTest** - Testet die REST-Endpoints

```bash
# Alle Tests ausführen
mvn test

# Nur bestimmte Tests
mvn test -Dtest=HelloControllerTest
```

## 📡 API-Endpoints

### GET /
Gibt eine Begrüßungsnachricht zurück.

**Response:**
```
Greetings from demo!
```

### GET /something
Gibt einen Status zurück.

**Response:**
```
All Ok
```

## ⚙️ Konfiguration

Die Anwendungskonfiguration befindet sich in `src/main/resources/application.properties`:

```properties
spring.application.name=demo
```

## 🔧 Technologie-Stack

| Technologie | Version | Beschreibung |
|------------|---------|--------------|
| Spring Boot | 3.5.7 | Web-Framework |
| Java | 17 | Programmiersprache |
| Maven | 3.x | Build-Tool |
| JUnit Jupiter | 6.0.0 | Testing-Framework |
| Jib | 3.4.4 | Container-Builder |
| GitHub Actions | - | CI/CD Platform |

## 📝 Entwicklung

### Neuen Endpoint hinzufügen

1. Öffnen Sie `HelloController.java`
2. Fügen Sie eine neue Methode mit `@GetMapping` oder `@PostMapping` hinzu:

```java
@GetMapping("/new-endpoint")
public String newEndpoint() {
    return "Response";
}
```

3. Fügen Sie entsprechende Tests in `HelloControllerTest.java` hinzu
4. Commit und Push - die Pipeline wird automatisch ausgeführt

### Maven-Befehle

```bash
mvn clean                    # Build-Verzeichnis löschen
mvn compile                  # Projekt kompilieren
mvn test                     # Tests ausführen
mvn package                  # JAR-Datei erstellen
mvn spring-boot:run          # Anwendung starten
mvn jib:dockerBuild         # Docker-Image bauen
```

## 🤝 Beitragen

1. Fork das Repository
2. Erstellen Sie einen Feature-Branch (`git checkout -b feature/AmazingFeature`)
3. Committen Sie Ihre Änderungen (`git commit -m 'Add some AmazingFeature'`)
4. Pushen Sie zum Branch (`git push origin feature/AmazingFeature`)
5. Öffnen Sie einen Pull Request

## 📄 Lizenz

Dieses Projekt ist für Bildungszwecke erstellt.

## 👥 Autoren

Demo-Projekt für Spring Boot CI/CD mit GitHub Actions

## 🔗 Weiterführende Links

- [Spring Boot Dokumentation](https://spring.io/projects/spring-boot)
- [Jib Documentation](https://github.com/GoogleContainerTools/jib)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Documentation](https://maven.apache.org/)

---

**Hinweis:** Stellen Sie sicher, dass Sie die GitHub Packages-Berechtigung in Ihren Repository-Einstellungen aktiviert haben, damit die Pipeline Docker-Images pushen kann.


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

### Option 1: Mit Jib

Jib erstellt Docker-Images ohne Docker-Daemon - schneller und effizienter.

```bash
# Docker-Image in lokalen Docker-Daemon bauen
mvn compile jib:dockerBuild

# Direkt zu Registry pushen (ohne Docker)
mvn compile jib:build

# Als Tar-Datei exportieren
mvn compile jib:buildTar
```

Weitere Details siehe [JIB-USAGE.md](JIB-USAGE.md)

### Option 2: Mit klassischem Dockerfile

Der Dockerfile verwendet ein bereits gebautes JAR - Sie müssen zuerst mit Maven bauen.

**Build und Run:**
```bash
# Schritt 1: JAR mit Maven bauen
mvn clean package

# Schritt 2: Docker-Image erstellen
docker build -t demo-app:latest .

# Schritt 3: Container starten
docker run -p 8080:8080 demo-app:latest

# Mit Environment-Variablen
docker run -p 8080:8080 \
  -e SPRING_APPLICATION_NAME=my-demo \
  demo-app:latest

# Im Hintergrund starten
docker run -d -p 8080:8080 --name demo-container demo-app:latest

# Logs anzeigen
docker logs demo-container

# Health-Check Status
docker inspect --format='{{.State.Health.Status}}' demo-container
```

### Docker-Befehle - Übersicht

```bash
# JAR bauen und Docker-Image erstellen
mvn clean package
docker build -t demo-app:latest .

# Alle Images anzeigen
docker images

# Container starten
docker run -p 8080:8080 demo-app:latest

# Container im Hintergrund starten
docker run -d -p 8080:8080 --name demo demo-app:latest

# Laufende Container anzeigen
docker ps

# Container stoppen
docker stop demo

# Container entfernen
docker rm demo

# Image löschen
docker rmi demo-app:latest

# In laufenden Container einloggen
docker exec -it demo sh

# Container-Logs verfolgen
docker logs -f demo

# Image zu Registry pushen
docker tag demo-app:latest registry.example.com/demo-app:latest
docker push registry.example.com/demo-app:latest
```

### Dockerfile-Details

**Base Image:** eclipse-temurin:17-jre-alpine  
**Voraussetzung:** JAR muss mit `mvn clean package` bereits gebaut sein  
**Security:** Läuft als non-root User (spring:spring)  
**Health-Check:** Automatische Gesundheitsprüfung alle 30 Sekunden  
**Image-Größe:** ~200MB (Alpine-basiert)

**Vergleich der Methoden:**

| Feature | Jib | Dockerfile |
|---------|-----|------------|
| Docker-Daemon nötig | ❌ Nein | ✅ Ja |
| Maven-Build vorher | ⚠️ Optional | ✅ Erforderlich |
| Build-Geschwindigkeit | ⚡ Sehr schnell | ⚡ Schnell |
| Image-Größe | 📦 Klein | 📦 Klein |
| Layer-Optimierung | ✅ Automatisch | ⚠️ Basic |
| Reproduzierbarkeit | ✅ Hoch | ✅ Hoch |
| Einfachheit | ✅ Sehr einfach | ✅ Einfach |
| Lokal bauen | ✅ Ja | ✅ Ja |
| Direkt zu Registry | ✅ Ja | ❌ Nein |

### Container starten

```bash
# Standard
docker run -p 8080:8080 demo-app:latest

# Mit custom Port
docker run -p 9090:8080 demo-app:latest

# Mit Volume (für Logs)
docker run -p 8080:8080 \
  -v $(pwd)/logs:/app/logs \
  demo-app:latest

# Mit Environment-Variablen
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e JAVA_OPTS="-Xmx1g" \
  demo-app:latest

# Mit Memory-Limits
docker run -p 8080:8080 \
  --memory="512m" \
  --cpus="1.0" \
  demo-app:latest
```

## 🔄 CI/CD Pipeline

Das Projekt enthält **zwei vollautomatische GitHub Actions Pipelines**, die bei jedem Push auf den `master`-Branch ausgeführt werden.

### Pipeline 1: CI/CD mit Jib (`.github/workflows/main.yml`)

**Empfohlen für:** Schnelle Builds, direkte Registry-Pushes ohne Docker-Daemon

#### Job 1: Build and Test
1. ✅ Code auschecken
2. ✅ JDK 17 einrichten
3. ✅ Projekt kompilieren
4. ✅ Unit Tests ausführen
5. ✅ JAR-Datei paketieren
6. ✅ Build-Artefakte hochladen

#### Job 2: Docker Build & Push (mit Jib)
(Wird nur ausgeführt, wenn Job 1 erfolgreich war)

1. ✅ Code auschecken
2. ✅ JDK 17 einrichten
3. ✅ Bei GitHub Container Registry anmelden
4. ✅ Docker-Image mit Jib bauen und pushen zu **GitHub Packages (ghcr.io)**
5. ✅ Image-Details ausgeben

**Image abrufen:**
```bash
# Latest Version
docker pull ghcr.io/<github-username>/demo:latest

# Spezifische Version (Commit SHA)
docker pull ghcr.io/<github-username>/demo:<commit-sha>
```

---

### Pipeline 2: CI/CD mit Docker CLI (`.github/workflows/docker-cli.yml`)

**Empfohlen für:** Standard Docker-Workflows, DockerHub-Integration

#### Job 1: Build and Test
1. ✅ Code auschecken
2. ✅ JDK 17 einrichten
3. ✅ Projekt kompilieren
4. ✅ Unit Tests ausführen
5. ✅ JAR-Datei paketieren
6. ✅ JAR-Artefakt hochladen

#### Job 2: Docker Build & Push (mit Docker CLI)
(Wird nur ausgeführt, wenn Job 1 erfolgreich war)

1. ✅ Code auschecken
2. ✅ JAR-Artefakt herunterladen
3. ✅ Docker Buildx einrichten
4. ✅ Bei DockerHub anmelden
5. ✅ Docker-Metadata extrahieren (Tags, Labels)
6. ✅ Docker-Image bauen und zu **DockerHub** pushen
7. ✅ Image-Details ausgeben

**Automatische Tags:**
- `latest` (nur auf master-Branch)
- `<branch-name>-<commit-sha>` (z.B. `master-abc1234`)
- Branch-Name (z.B. `master`)
- PR-Nummer (bei Pull Requests)

**Image abrufen:**
```bash
# Latest Version
docker pull <dockerhub-username>/demo:latest

# Spezifische Version
docker pull <dockerhub-username>/demo:master-<commit-sha>
```

---

### 🔐 Secrets-Konfiguration für DockerHub-Pipeline

Um die Docker CLI Pipeline zu verwenden, müssen Sie folgende Secrets in Ihrem GitHub-Repository konfigurieren:

1. Gehen Sie zu Ihrem GitHub-Repository
2. **Settings** → **Secrets and variables** → **Actions** → **New repository secret**

Fügen Sie folgende Secrets hinzu:

| Secret Name | Beschreibung | Beispiel |
|------------|--------------|----------|
| `DOCKERHUB_USERNAME` | Ihr DockerHub-Benutzername | `myusername` |
| `DOCKERHUB_TOKEN` | DockerHub Access Token | `dckr_pat_...` |

**DockerHub Access Token erstellen:**
1. Melden Sie sich bei [DockerHub](https://hub.docker.com/) an
2. **Account Settings** → **Security** → **New Access Token**
3. Geben Sie einen Namen ein (z.B. "GitHub Actions")
4. Wählen Sie **Read, Write, Delete** Permissions
5. Kopieren Sie das generierte Token
6. Fügen Sie es als `DOCKERHUB_TOKEN` Secret in GitHub ein

---

### Pipeline-Vergleich

| Feature | Jib Pipeline | Docker CLI Pipeline |
|---------|--------------|---------------------|
| Workflow-Datei | `main.yml` | `docker-cli.yml` |
| Build-Tool | Jib | Docker CLI |
| Target Registry | GitHub Packages (ghcr.io) | DockerHub |
| Docker-Daemon nötig | ❌ Nein | ✅ Ja (in GitHub Runner) |
| Build-Geschwindigkeit | ⚡ Sehr schnell | ⚡ Schnell |
| Layer-Caching | ✅ Automatisch | ✅ GitHub Actions Cache |
| Secrets benötigt | ❌ Nein (GITHUB_TOKEN) | ✅ Ja (DockerHub) |
| Image-Tags | SHA + latest | Flexible (Branch, SHA, PR) |
| Komplexität | ✅ Einfach | ⚠️ Mittel |

---

### Pipeline manuell auslösen

Beide Pipelines können auch manuell ausgelöst werden:

1. Gehen Sie zu **Actions** in Ihrem GitHub-Repository
2. Wählen Sie die gewünschte Pipeline:
   - "CI/CD" (Jib)
   - "CI/CD with Docker CLI"
3. Klicken Sie auf **Run workflow**
4. Wählen Sie den Branch und klicken Sie auf **Run workflow**

---

### Beide Pipelines parallel nutzen

Sie können beide Pipelines gleichzeitig aktiviert lassen. Jeder Push wird dann:
- Ein Image zu **GitHub Packages** pushen (via Jib)
- Ein Image zu **DockerHub** pushen (via Docker CLI)

Dies ist nützlich für:
- ✅ Redundanz (mehrere Registries)
- ✅ Verschiedene Zielgruppen (GitHub vs. öffentlich)
- ✅ Backup-Strategie

# Jib Docker Build - Anleitung

## Überblick
Das Projekt ist jetzt mit dem Jib Maven Plugin konfiguriert, um Docker-Container ohne Docker-Daemon zu erstellen.

## Konfiguration

### Image-Details
- **Basis-Image:** eclipse-temurin:17-jre
- **Image-Name:** cd/pipeline/demo
- **Tags:** 0.0.1-SNAPSHOT, latest
- **Port:** 8080
- **JVM-Optionen:** 512MB Heap (Min/Max)

## Verwendung

### 1. Docker-Image in lokalen Docker-Daemon bauen
```bash
mvn clean compile jib:dockerBuild
```
Erstellt das Image und lädt es in Ihren lokalen Docker-Daemon.

### 2. Direkt in Registry pushen (ohne Docker)
```bash
mvn clean compile jib:build
```
Erstellt das Image und pusht es direkt in eine Container-Registry (keine Docker-Installation erforderlich).

### 3. Als Tar-Datei exportieren
```bash
mvn clean compile jib:buildTar
```
Erstellt eine Tar-Datei des Images im `target/jib-image.tar` Verzeichnis.

## Container starten

Nach dem Build können Sie den Container mit Docker starten:

```bash
docker run -p 8080:8080 cd/pipeline/demo:latest
```

Oder mit einem spezifischen Tag:

```bash
docker run -p 8080:8080 cd/pipeline/demo:0.0.1-SNAPSHOT
```

## Testen

Die Anwendung ist dann unter folgenden URLs erreichbar:

- http://localhost:8080/ - Zeigt "Greetings from demo!"
- http://localhost:8080/something - Zeigt "All Ok"

## Erweiterte Konfiguration

### Image-Namen ändern
In der `pom.xml` unter `<configuration><to><image>` können Sie den Image-Namen anpassen:

```xml
<to>
    <image>mein-registry/mein-image</image>
</to>
```

### Registry-Authentifizierung
Für private Registries können Sie Credentials konfigurieren:

```bash
mvn compile jib:build -Djib.to.auth.username=<username> -Djib.to.auth.password=<password>
```

Oder in der `pom.xml`:

```xml
<to>
    <image>registry.example.com/my-image</image>
    <auth>
        <username>${env.REGISTRY_USERNAME}</username>
        <password>${env.REGISTRY_PASSWORD}</password>
    </auth>
</to>
```

## Vorteile von Jib

✅ Kein Docker-Daemon erforderlich  
✅ Schnellere Builds durch intelligentes Layer-Caching  
✅ Reproduzierbare Builds  
✅ Optimierte Layer-Struktur (Dependencies getrennt vom Code)  
✅ Integration in Maven/Gradle Build-Prozess  

## Troubleshooting

### Docker nicht verfügbar
Wenn `jib:dockerBuild` fehlschlägt, verwenden Sie stattdessen `jib:build` oder `jib:buildTar`.

### Permissions-Fehler
Stellen Sie sicher, dass Docker läuft und Sie die entsprechenden Berechtigungen haben.

### Build-Cache löschen
```bash
mvn clean
```


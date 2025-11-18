# Music Playlist — Entregable 4

Breve guía para compilar, probar, ejecutar, desplegar localmente y configurar Jenkins para el proyecto "Music Playlist".
## Estado actual
- Backend: Spring Boot (Thymeleaf) — Java 21
- Persistencia: archivo JSON local (`playlist_data.json`) (implementado en `PlaylistService`)
- UI: `src/main/resources/templates/playlist.html` (Bootstrap)
- CI/CD: `Jenkinsfile` (pipeline multiplataforma)
- Scripts de despliegue: `deploy-mac.sh` (macOS/Linux), `deploy-windows.bat` (Windows)
----

## Requisitos locales
- JDK 21 (o compatible)
- Maven (recomendado) — si no lo tenés, instalálo con Chocolatey en Windows: `choco install maven -y` o descarga desde https://maven.apache.org/
Nota: el repositorio incluye `wrapper/maven-wrapper.properties` pero no el wrapper completo. Por eso `mvnw.cmd` puede fallar; si no querés instalar Maven puedes pedirme que agregue el wrapper completo.

## Compilar y ejecutar tests
Desde la raíz del proyecto (donde está `pom.xml`):

```powershell
mvn -v
mvn test
```
Los tests unitarios añadidos cubren las operaciones principales de `PlaylistService` (add/delete/like/favorite y persistencia usando un archivo temporal).

## Ejecutar la aplicación localmente
1. Compilar y empaquetar:

```powershell
mvn package
```

2. Ejecutar el JAR:
```powershell
cd target
java -jar music-playlist-0.0.1-SNAPSHOT.jar
```
Por defecto la aplicación corre en el puerto configurado en `src/main/resources/application.properties` (8081).

## Endpoints principales
- GET `/` — muestra la página con la playlist
- POST `/add` — parámetros `title`, `url` (formulario en la UI)
- POST `/delete/{id}` — borra el video con id
- POST `/like/{id}` — suma 1 like al video
- POST `/favorite/{id}` — marca/desmarca favorito

## Persistencia
- La lista se guarda en `playlist_data.json` (archivo creado en el directorio desde donde se ejecute la app). Si querés otra ruta, se puede parametrizar fácilmente.
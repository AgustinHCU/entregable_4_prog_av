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

## Scripts de despliegue
- `deploy-mac.sh` (macOS/Linux): usa variables `DEPLOY_DIR` y `DEPLOY_PORT`. Ejemplo:

```bash
DEPLOY_DIR=$HOME/playlist-deploy DEPLOY_PORT=8081 ./deploy-mac.sh
```

- `deploy-windows.bat` (Windows PowerShell): se puede establecer variables de entorno antes de ejecutar:
```powershell
$env:DEPLOY_DIR='C:\playlist-deploy'; $env:DEPLOY_PORT='8081'; .\deploy-windows.bat
```

Ambos scripts intentan detener instancias previas de la aplicación de forma segura y escriben `app.pid` en el directorio de despliegue.
## Jenkins — configuración básica
Recomendado: ejecutar Jenkins con al menos un nodo (agent) que tenga Maven y Java instalados.

1. Plugins útiles en Jenkins:
	- Pipeline
	- Pipeline: Maven Integration Plugin (opcional)

2. Configurar herramientas globales (opcional):
	- En **Manage Jenkins > Global Tool Configuration** podés añadir Maven si preferís usar el plugin `withMaven`.

3. Cómo usar el `Jenkinsfile` incluido:
	- El `Jenkinsfile` detecta la plataforma (`isUnix()`) y usa `sh` o `bat` para compilar, testear, empaquetar y desplegar con los scripts `deploy-mac.sh` / `deploy-windows.bat`.
	- Asegurate que el workspace del agent tenga permisos para ejecutar los scripts y que `mvn` esté disponible en PATH.

4. Despliegue desde Jenkins:
	- El pipeline ejecutará `deploy-mac.sh` en nodos Unix y `deploy-windows.bat` en Windows.
	- Si querés personalizar la carpeta de despliegue, exportá `DEPLOY_DIR` y `DEPLOY_PORT` como variables de entorno en el job.

## Notas sobre el Maven Wrapper
- Actualmente falta la carpeta `.mvn/wrapper` necesaria para ejecutar `./mvnw`. Si preferís no instalar Maven en la máquina donde se ejecuta Jenkins, puedo agregar el wrapper completo al repositorio.

## Generar ZIP para la entrega
El ZIP debe incluir todo el código fuente, `pom.xml`, `Jenkinsfile`, scripts de despliegue y el informe PDF. Nombre sugerido: `Entregable2Apellido1Apellido2.zip`.

----

Si querés, ahora:
- Puedo agregar el Maven Wrapper completo para que `mvnw.cmd` funcione sin instalar Maven, o
- Puedo generar el borrador del informe en Markdown listo para convertir a PDF.

Decime cuál preferís y lo preparo.
# entregable_4_prog_av
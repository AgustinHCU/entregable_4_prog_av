@echo off
echo Stopping existing application...
taskkill /F /IM java.exe 2>nul

echo Copying JAR...
if not exist "C:\playlist-deploy" mkdir C:\playlist-deploy
copy target\*.jar C:\playlist-deploy\app.jar

echo Starting application...
cd C:\playlist-deploy
start /B java -jar app.jar > app.log 2>&1

echo Deployment complete!
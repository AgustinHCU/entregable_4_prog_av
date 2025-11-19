@echo off
setlocal enableextensions enabledelayedexpansion

REM Configurable variables
if "%DEPLOY_DIR%"=="" set "DEPLOY_DIR=C:\playlist-deploy"
if "%DEPLOY_PORT%"=="" set "DEPLOY_PORT=8081"

echo Using %DEPLOY_DIR%
if not exist "%DEPLOY_DIR%" mkdir "%DEPLOY_DIR%"

echo Stopping existing application if running...

if exist "%DEPLOY_DIR%\app.pid" (
    for /f "usebackq delims=" %%p in ("%DEPLOY_DIR%\app.pid") do set "OLD_PID=%%p"
    if defined OLD_PID (
        echo Stopping PID %OLD_PID%
        taskkill /PID %OLD_PID% /F >nul 2>&1
        del "%DEPLOY_DIR%\app.pid" >nul 2>&1
    )
)

echo Copying JAR...
copy /Y target\music-playlist-0.0.1-SNAPSHOT.jar "%DEPLOY_DIR%\app.jar" >nul

timeout /t 2 >nul

echo Starting application...
pushd "%DEPLOY_DIR%"
powershell -NoProfile -Command ^
    "& { $p = Start-Process -FilePath 'java' -ArgumentList '-jar','app.jar' -PassThru; [IO.File]::WriteAllText('app.pid', $p.Id.ToString()) }"
popd

echo Deployment complete! App started on port %DEPLOY_PORT%

endlocal

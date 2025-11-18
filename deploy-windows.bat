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
		taskkill /PID %OLD_PID% /F >nul 2>&1 || echo Failed to stop %OLD_PID%
		del "%DEPLOY_DIR%\app.pid" >nul 2>&1 || rem ignore
	)
) else (
	REM Fallback: try to find java process that runs app.jar using WMIC (may be unavailable on some Windows)
	for /f "tokens=2 delims==" %%i in ('wmic process where "CommandLine like '%%app.jar%%'" get ProcessId /value 2^>nul ^| findstr /r /v "^$"') do (
		echo Stopping process %%i
		taskkill /PID %%i /F >nul 2>&1 || echo Failed to stop %%i
	)
)

echo Copying JAR...
copy /Y target\*.jar "%DEPLOY_DIR%\app.jar" >nul

echo Starting application...
pushd "%DEPLOY_DIR%"
REM Use PowerShell to start the process and capture PID
powershell -NoProfile -Command "& { $p = Start-Process -FilePath 'java' -ArgumentList '-jar','app.jar' -PassThru; [IO.File]::WriteAllText('app.pid', $p.Id.ToString()) }"

echo Deployment complete! App started on port %DEPLOY_PORT%
popd

endlocal
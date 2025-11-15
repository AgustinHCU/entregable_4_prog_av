#!/bin/bash
set -e  # Exit on error

echo "==================== DEPLOYMENT STARTED ===================="
echo "Timestamp: $(date)"
echo "Working directory: $(pwd)"

echo ""
echo "Step 1: Stopping existing application..."
pkill -f "playlist.*jar" || true
sleep 2

echo ""
echo "Step 2: Finding JAR file..."
JAR_FILE=$(ls target/*.jar 2>/dev/null | head -1)

if [ -z "$JAR_FILE" ]; then
    echo "ERROR: No JAR file found in target/"
    echo "Contents of target/:"
    ls -la target/ || echo "target/ directory not found"
    exit 1
fi

echo "Found JAR: $JAR_FILE"
echo "JAR size: $(du -h "$JAR_FILE" | cut -f1)"

echo ""
echo "Step 3: Setting up deployment directory..."
DEPLOY_DIR="$HOME/playlist-deploy"
mkdir -p $DEPLOY_DIR
echo "Deploy directory: $DEPLOY_DIR"

echo ""
echo "Step 4: Copying JAR..."
cp -v "$JAR_FILE" $DEPLOY_DIR/app.jar

echo ""
echo "Step 5: Starting application..."
cd $DEPLOY_DIR
echo "Current directory: $(pwd)"
echo "Java version: $(java -version 2>&1 | head -1)"

# Start with detailed logging - disown to prevent termination when parent exits
nohup java -jar app.jar > app.log 2>&1 &
APP_PID=$!
disown

echo "Application started with PID: $APP_PID"
echo ""
echo "Step 6: Checking application status..."
sleep 5

# Check if process is still running
if ps -p $APP_PID > /dev/null 2>&1; then
   echo "✓ Process is running (PID: $APP_PID)"
   
   # Check if port is listening
   if lsof -i :8081 > /dev/null 2>&1; then
      echo "✓ Port 8081 is open"
      echo ""
      echo "==================== DEPLOYMENT SUCCESSFUL ===================="
      echo "Access at: http://localhost:8081"
      echo "View logs: tail -f ~/playlist-deploy/app.log"
   else
      echo "⚠ Port 8081 is NOT open yet, app may still be starting..."
      echo "Recent logs:"
      tail -20 app.log
   fi
else
   echo "✗ Application failed to start!"
   echo ""
   echo "==================== ERROR LOGS ===================="
   cat app.log
   echo "==================== END LOGS ===================="
   exit 1
fi
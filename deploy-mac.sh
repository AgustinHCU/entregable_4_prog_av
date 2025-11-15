#!/bin/bash

echo "Stopping existing application..."
pkill -f "music-playlist.*jar" || true
pkill -f "app.jar" || true
sleep 3

echo "Copying JAR to deployment directory..."
DEPLOY_DIR="$HOME/playlist-deploy"
mkdir -p $DEPLOY_DIR
cp target/*.jar $DEPLOY_DIR/app.jar

echo "Starting application..."
cd $DEPLOY_DIR
nohup java -jar app.jar > app.log 2>&1 &

echo "Deployment complete! App running on port 8081"
echo "Access at: http://localhost:8081"
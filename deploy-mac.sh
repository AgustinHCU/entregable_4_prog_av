#!/usr/bin/env bash
set -euo pipefail

# Configurable variables
DEPLOY_DIR="${DEPLOY_DIR:-$HOME/playlist-deploy}"
DEPLOY_PORT="${DEPLOY_PORT:-8081}"

echo "Using DEPLOY_DIR=$DEPLOY_DIR"
mkdir -p "$DEPLOY_DIR"

echo "Stopping existing application if running..."
# Prefer PID file if present
if [ -f "$DEPLOY_DIR/app.pid" ]; then
	OLD_PID=$(cat "$DEPLOY_DIR/app.pid" || true)
	if [ -n "$OLD_PID" ]; then
		echo "Stopping PID $OLD_PID"
		kill "$OLD_PID" 2>/dev/null || true
		sleep 2
	fi
fi

# Fallback: try to stop process listening on DEPLOY_PORT
if command -v lsof >/dev/null 2>&1; then
	PIDS=$(lsof -ti :"$DEPLOY_PORT" || true)
	if [ -n "$PIDS" ]; then
		echo "Killing process(es) on port $DEPLOY_PORT: $PIDS"
		kill $PIDS || true
		sleep 2
	fi
else
	pkill -f "app.jar" || true
fi

echo "Copying JAR to deployment directory..."
cp target/*.jar "$DEPLOY_DIR/app.jar"

echo "Starting application..."
cd "$DEPLOY_DIR"
nohup java -jar app.jar > app.log 2>&1 &
NEW_PID=$!
echo "$NEW_PID" > app.pid

echo "Deployment complete! App running on port $DEPLOY_PORT (pid $NEW_PID)"
echo "Access at: http://localhost:$DEPLOY_PORT"
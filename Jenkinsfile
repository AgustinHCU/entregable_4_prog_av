pipeline {
    agent { 
        node {
            label 'macos'
        }
    }
    tools {
        maven 'Maven'  // This name must match what you named it in Jenkins
    }
    stages {
        stage('Build') {
            steps {
                echo "Building with Maven..."
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                echo "Running tests..."
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo "Packaging application..."
                sh 'mvn package -DskipTests'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                sh '''
                if [[ "$OSTYPE" == "darwin"* ]]; then
                    JENKINS_NODE_COOKIE=dontKillMe ./deploy-mac.sh
                fi
                '''
            }
        }
    }
}

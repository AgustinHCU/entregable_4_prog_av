pipeline {
    agent { 
        node {
            label 'macos'
        }
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
                    ./deploy-mac.sh
                fi
                '''
            }
        }
    }
}

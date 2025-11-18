pipeline {
    agent any
    options { timestamps() }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                echo "Building with Maven..."
                script {
                    if (isUnix()) {
                        sh 'mvn -B -DskipTests=false clean compile'
                    } else {
                        bat 'mvn -B -DskipTests=false clean compile'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                echo "Running tests..."
                script {
                    if (isUnix()) {
                        sh 'mvn -B test'
                    } else {
                        bat 'mvn -B test'
                    }
                }
            }
        }
        stage('Package') {
            steps {
                echo "Packaging application..."
                script {
                    if (isUnix()) {
                        sh 'mvn -B package -DskipTests'
                    } else {
                        bat 'mvn -B package -DskipTests'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                script {
                    if (isUnix()) {
                        // prefer deploy-mac.sh for Unix-like nodes
                        sh 'chmod +x ./deploy-mac.sh || true'
                        sh './deploy-mac.sh'
                    } else {
                        bat '.\\deploy-windows.bat'
                    }
                }
            }
        }
    }
    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Build failed — check logs.'
        }
    }
}

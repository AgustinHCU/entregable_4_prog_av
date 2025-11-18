pipeline {
    agent any
    options { timestamps() }

    environment {
        MAVEN_HOME = tool 'Maven-3.9.11'
        MVN = "${MAVEN_HOME}/bin/mvn"
    }

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
                        sh "\"${MVN}\" -B -DskipTests=false clean compile"
                    } else {
                        bat "\"%MVN%\" -B -DskipTests=false clean compile"
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                script {
                    if (isUnix()) {
                        sh "\"${MVN}\" -B test"
                    } else {
                        bat "\"%MVN%\" -B test"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                echo "Packaging application..."
                script {
                    if (isUnix()) {
                        sh "\"${MVN}\" -B package -DskipTests"
                    } else {
                        bat "\"%MVN%\" -B package -DskipTests"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                script {
                    if (isUnix()) {
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


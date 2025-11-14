pipeline {
    agent any

    tools {
        maven 'Maven3'   // Nombre configurado en Global Tool Configuration
        jdk 'JDK17'      // O el nombre de tu instalación de Java
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out source code.."
                git branch: 'main',
                    url: 'https://github.com/AgustinHCU/entregable_4_prog_av.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building project with Maven..."
                sh "mvn -B -e -DskipTests clean package"
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                sh "mvn test"
            }
        }

        stage('Deliver') {
            steps {
                echo "Packaging and delivering..."
                sh '''
                echo "Resulting JAR files in target/:"
                ls -lh target
                '''
            }
        }
    }
}

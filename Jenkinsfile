pipeline {
    agent any
    
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
                echo "Building.."
                sh '''
                echo "Compiling Java classes.."
                mkdir -p target/classes
                javac -d target/classes src/main/java/streaming/*.java
                '''
            }
        }
        
        stage('Test') {
            steps {
                echo "Testing.."
                sh '''
                echo "Running manual test (no JUnit available).."
                # Run main class as test
                java -cp target/classes streaming.Streaming
                '''
            }
        }
        
        stage('Deliver') {
            steps {
                echo 'Delivering application..'
                sh '''
                echo "Packaging application.."
                jar cfe streaming.jar streaming.Streaming -C target/classes .
                echo "Application packaged successfully!"
                '''
            }
        }
    }
}

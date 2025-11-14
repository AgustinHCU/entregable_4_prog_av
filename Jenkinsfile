pipeline {
    agent { 
        node {
            label ''
        }
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "Checking out source code.."
                git branch: 'main', 
                    url: 'https://github.com/your-username/mi-playlist.git'
            }
        }
        
        stage('Build') {
            steps {
                echo "Building.."
                sh '''
                echo "Compiling Java classes.."
                javac -d target/classes src/main/java/com/playlist/**/*.java
                '''
            }
        }
        
        stage('Test') {
            steps {
                echo "Testing.."
                sh '''
                echo "Running basic tests.."
                # Simple test execution
                java -cp target/classes com.playlist.AppTest
                '''
            }
        }
        
        stage('Deliver') {
            steps {
                echo 'Delivering application..'
                sh '''
                echo "Creating JAR package.."
                jar cfe mi-playlist.jar com.playlist.Main -C target/classes .
                echo "Application packaged successfully!"
                '''
            }
        }
    }
}

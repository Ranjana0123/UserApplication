pipeline {
    agent any

    environment {
        IMAGE_NAME = 'userapp'
        CONTAINER_NAME = 'userapp-container'
        DOCKER_REGISTRY = '' // optional if pushing to registry
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out source code...'
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                echo '🔧 Building the Spring Boot project...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                bat "docker build -t %IMAGE_NAME% ."
            }
        }

        stage('Stop & Remove Existing Container') {
            steps {
                echo '🧹 Cleaning up old containers if any...'
                bat '''
                docker stop %CONTAINER_NAME%
                IF %ERRORLEVEL% NEQ 0 (
                    echo Container may not exist. Ignoring...
                )

                docker rm %CONTAINER_NAME%
                IF %ERRORLEVEL% NEQ 0 (
                    echo Container may already be removed. Ignoring...
                )

                exit 0
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                echo '🚀 Starting Docker container...'
                bat '''
                docker run -d -p 8080:8080 --name %CONTAINER_NAME% %IMAGE_NAME%
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Build and deployment succeeded!'
        }

        failure {
            echo '❌ Build Failed ❌'
        }
    }
}

pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'jenkins-maven' // Set this in Jenkins tools config
        DOCKER_IMAGE = 'userapp-image'
        CONTAINER_NAME = 'userapp-container'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat "${MAVEN_HOME}/bin/mvn clean install -DskipTests"
            }
        }

       stage('Build Docker Image') {
                   steps {
                       echo '🐳 Building Docker image...'
                       bat "docker build -t %DOCKER_IMAGE% ."
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
                       docker run -d -p 8080:8080 --name %CONTAINER_NAME% %DOCKER_IMAGE%
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
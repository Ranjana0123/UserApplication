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
                script {
                    docker.build("${DOCKER_IMAGE}")
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Stop and remove old container if exists
                    sh "docker stop ${CONTAINER_NAME} || true"
                    sh "docker rm ${CONTAINER_NAME} || true"

                    // Run new container
                    sh "docker run -d -p 8080:8080 --name ${CONTAINER_NAME} ${DOCKER_IMAGE}"
                }
            }
        }
    }

    post {
        failure {
            echo 'Build Failed ❌'
        }
        success {
            echo 'Spring Boot App Deployed in Docker ✅'
        }
    }
}

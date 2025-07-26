pipeline {
    agent any

    tools {
        maven 'jenkins-maven' // this matches the name in Jenkins' Global Tool Config
    }

    environment {
        MAVEN_HOME = tool 'jenkins-maven'
        PATH = "${MAVEN_HOME}/bin;${env.PATH}"  // make sure mvn is available in PATH
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploy stage - add deployment logic here'
            }
        }
    }
}

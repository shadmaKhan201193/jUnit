pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                sh "gradle --version"
                sh "gradle clean build"
            }
        }
    }

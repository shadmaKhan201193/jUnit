@Library('sharedlibrary') _
pipeline {
    agent any
    tools {
        gradle "7.4.2"
        jdk "JDK17"
        dockerTool "docker"
    }
    stages {
        stage("Build") {
            steps {
                script{
                    build()
                }
            }
        }   
        stage("Scan") {
            steps {
                script {
                    sonarScan()
                }
            }
        }
        stage("Publish"){
            steps{
                script {
                    dockerPublish()
                }
            }
        }
    }
}

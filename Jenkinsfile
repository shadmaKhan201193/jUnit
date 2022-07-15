@Library('sharedlibrary') _
pipeline {
    agent any
    tools {
        gradle "7.4.2"
        jdk "JDK17"
        dockerTool "docker"
    }
    environment {
        dockerhub=credentials("dockerhub")
    }
    stages {
        stage("Build") {
            steps {
                script{
                    build()
                }
            }
        }   
        stage("Sonar Scan") {
            steps {
                script {
                    sonarScan()
                }
            }
        }
        stage(docker){
            steps{
                sh "docker build . -t 172.21.0.66:5000/master-service:1.0"
                //sh "docker login 172.21.0.66:5000 -u $dockerhub_USR -p $dockerhub_PSW"
                sh "docker push 172.21.0.66:5000/master-service:1.0"
            }
        }
    }
}

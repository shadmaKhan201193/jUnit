@Library('sharedlibrary') _
pipeline {
    agent any
    tools {
        gradle "7.4.2"
        jdk "JDK17"
        docker "docker"
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
                    def sonarqubeScannerHome = tool "sonar-scanner";
                    withSonarQubeEnv('sonarqube') {
                        //def scannerHome = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                        sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=masterservice -Dsonar.language=java -Dsonar.sources=src/main/java -Dsonar.java.binaries=build/classes -Dsonar.scm.disabled=true"
                    }
                    timeout(time: 10, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }
        stage(docker){
            steps{
                sh "docker ps"
            }
        }
    }
}

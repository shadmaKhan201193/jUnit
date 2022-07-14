@Library('sharedlibrary') _
      pipeline {
        agent any
        tools {
            gradle "7.4.2"
            jdk "JDK17"
            //SonarQube Scanner "sonarqube"
        }

        stages {
            stage("Build") {
                steps {
                    script{
                        build()
                    }
                }
            }   
            stage("SonarQube analysis") {
                steps {
                    script {
                       def sonarqubeScannerHome = tool "sonar-scanner";
                        withSonarQubeEnv('sonarqube') {
                            //def scannerHome = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                            sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=masterservice -Dsonar.language=java -Dsonar.sources=src/main/java -Dsonar.java.binaries=build/classes -Dsonar.scm.disabled=true"
                        }
                    }
                }
            }
            stage("Quality Gate") {
                steps {
                    timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

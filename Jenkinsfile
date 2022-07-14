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
                            sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.host.url=http://172.21.0.66:9000 -Dsonar.projectName=masterservice -Dsonar.projectKey=masterservice -Dsonar.sources=/src/main/ -Dsonar.language=java -Dsonar.java.binaries=."
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

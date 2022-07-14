@Library('sharedlibrary') _
      pipeline {
        agent any
        tools {
            gradle "7.4.2"
            jdk "JDK17"
            sonarqube "sonarqube"
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
                    withSonarQubeEnv('sonarqube') {
                    //sh 'sonarscanner'
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

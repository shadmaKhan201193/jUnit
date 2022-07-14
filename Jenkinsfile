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
               //steps {
                    def scannerhome = tool "sonar-scanner";
                    withSonarQubeEnv('sonarqube') {
                        sh "${scannerhome}/bin/sonar-scanner"
                    }
                //}
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

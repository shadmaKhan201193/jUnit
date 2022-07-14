@Library('sharedlibrary') _
}
      pipeline {
        agent any
        stages {
            stage("Build") {
                steps {
                    script{
                        build()
                    }
                }
            }   
            stage("build & SonarQube analysis") {
                steps {
                    withSonarQubeEnv('My SonarQube Server') {
                    sh 'mvn clean package sonar:sonar'
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

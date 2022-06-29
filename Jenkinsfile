pipeline {
    agent any
    tools {
        gradle "7.4.2"
    }
    stages {
        stage("Build") {
            steps {
                sh "gradle clean build"
            }
        }
    }
}

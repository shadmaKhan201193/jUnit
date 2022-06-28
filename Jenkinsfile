pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                dir('.') {
                    sh 'gradle clean build'
                }
            }
        }
    }
}

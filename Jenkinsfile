pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                dir('.') {
                    sh 'clean build'
                }
            }
        }
    }
}

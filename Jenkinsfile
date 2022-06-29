pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                {
                    withgradle{
                        sh 'gradle clean build'
                    }
                    
                }
            }
        }
    }
}

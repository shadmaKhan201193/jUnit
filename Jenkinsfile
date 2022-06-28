pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                dir(''/var/jenkins_home/workspace/cnpoc_cnpoc_business_master_main'') {
                    sh 'gradle clean build'
                }
            }
        }
    }
}

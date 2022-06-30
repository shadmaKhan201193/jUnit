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
        stage("publish") {
            steps {
                nexusArtifactUploader artifacts: [[artifactId: '${group}', classifier: '', file: 'build/libs/', type: 'jar']], credentialsId: 'nexus', groupId: 'com.itl', nexusUrl: '127.21.0.66:8081', nexusVersion: 'nexus2', protocol: 'http', repository: 'maven-central-repo', version: ''
            }
        }
    }
}

@Library('sharedlibrary') _
pipeline {
    agent any
    tools {
        gradle "7.4.2"
    }
    stages {
        stage("Build") {
            steps {
                script{
                    build()
                }
            }
        }
/*        stage("publish") {
            steps {
                script{
                    publish()
                }
            }
        }
    }
*/
}

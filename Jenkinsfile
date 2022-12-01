@Library('sharedlibrary@helm-jacoco') _
pipeline {
    agent any
    tools {
        gradle "7.4.2"
        jdk "JDK17"
        dockerTool "docker"
    }
    stages {
        stage("Build") {
            steps {
                script{
                    build()
                }
            }
        }   
        stage("Scan") {
            steps {
                script {
                    sonarScan()
                }
            }
        }
        stage("Publish"){
            steps{
                script {
                    dockerPublish()
                }
            }
        }
        /*stage('Dependancy track') {
            steps {
                script {
                    dependencyTrackPublisher artifact: 'build/reports/bom.xml', autoCreateProjects: false, dependencyTrackApiKey: '', dependencyTrackFrontendUrl: '', dependencyTrackUrl: '', projectId: '34bd2295-67db-498c-9aed-b82face74940', projectName: '', projectVersion: '', synchronous: true
                }
            }
        }
        stage("test") {
            steps {
                    sh '''
                            helm repo add --username mayuresh.kosandar --password _zuaUmuzZEVVN_jY1X5F master http://gitlab.products.kiya.ai/api/v4/projects/109/packages/helm/stable
                        helm repo update
                        helm upgrade --install -f values.yaml master master/master-service --namespace cnpoc-dev
                    '''
            }
        }
        stage("Deploy") {
            steps {
                script {
                    deploy()
                }
            }
        }
        stage("namespaces") {
            steps {
                script {
                    helm()
                }
            }
        } */
    }
}



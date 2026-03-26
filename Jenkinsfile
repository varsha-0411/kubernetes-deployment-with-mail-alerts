pipeline {
    agent any

    tools {
        maven 'mymav'
    }

    stages {

        stage('Git Checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[credentialsId: 'dockerhub-creds', url: 'https://github.com/varsha-0411/kubernetes-deployment-with-mail-alerts.git']]
                )
            }
            post {
                success {
                    emailext(
                        subject: "Checkout Success",
                        body: "Git checkout completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }

        stage('Build WAR file') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    emailext(
                        subject: "WAR Build Success",
                        body: "WAR file build completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t chitti .'
            }
            post {
                success {
                    emailext(
                        subject: "Docker Build Success",
                        body: "Docker image build completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                sh 'docker tag chitti varsha0411/chitti:v1'
            }
            post {
                success {
                    emailext(
                        subject: "Docker Tag Success",
                        body: "Docker image tag completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
            post {
                success {
                    emailext(
                        subject: "Docker Login Success",
                        body: "Docker Hub login completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push varsha0411/chitti:v1'
            }
            post {
                success {
                    emailext(
                        subject: "Docker Push Success",
                        body: "Docker image push completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }

        stage('K8s Deployment') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh '''
                    export KUBECONFIG=$KUBECONFIG
                    kubectl get nodes
                    kubectl apply -f manifest.yml
                    '''
                }
            }
            post {
                success {
                    emailext(
                        subject: "K8s Deployment Success",
                        body: "Kubernetes deployment completed successfully",
                        to: "varshachowdary411@gmail.com"
                    )
                }
            }
        }
    }
}

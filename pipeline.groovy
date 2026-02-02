pipeline {
    agent { label 'slave' }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/abhayt7/student-ui-app.git'
            }
        }

        stage('Build') {
            steps {
                sh '/opt/maven/bin/mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh '''
                    /opt/maven/bin/mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=student-ui-app \
                      -Dsonar.projectName=student-ui-app
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Artifact Upload to S3') {
    steps {
        sh '''
            aws s3 cp target/studentapp-2.2-SNAPSHOT.war s3://anurag-1234/
        '''
      }
    }

        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-cred', path: '', url: 'http://13.220.108.183/:8080/')], contextPath: '/', war: '**/*war'
                echo "Deployment successful"
            }
        }
    }
}

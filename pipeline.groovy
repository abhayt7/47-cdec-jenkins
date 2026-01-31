pipeline {
    agent { label 'slave' }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
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

        stage('Deploy') {
            steps {
                echo "Deployment successful"
            }
        }
    }
}

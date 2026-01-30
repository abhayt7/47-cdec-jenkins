pipeline {
    agent { label 'slave' }
    stages {
        stage('git-pull-stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
            }
        }
         stage('build-stage') {
            steps {
                sh '/opt/maven/bin/mvn clean package'
            }
        }
         stage('test-stage') {
            steps {
                withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sona1r-cred') {
                      sh '/opt/maven/bin/mvn sonar:sonar'
                  }
                // sh '''/opt/maven/bin/mvn sonar:sonar \\
                //      -Dsonar.projectKey=studentapp \\
                //      -Dsonar.host.url=http://13.53.200.180:9000 \\
                //      -Dsonar.login=dfb451556e35114bf31b5798423e18c16fe839f2'''
  
            }
        }
         stage('Quality_Gate') {
            steps {
                timeout(3) {
   
            }
                waitForQualityGate true
            }
        }

         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}

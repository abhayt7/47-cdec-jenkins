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
         stage('tesr-stage') {
            steps {
                 withSonarQubeEnv(installationName: 'sonar',credentialsId: 'sonar_cred1') {
                      sh '/opt/maven/bin/mvn sonar:sonar'
                  }


                //  sh '''mvn sonar:sonar \\
                //       -Dsonar.projectKey=studentapp \\
                //        -Dsonar.host.url=http://13.213.70.190:9000 \\
                //        -Dsonar.login=31ea99c3250677b2342707341800c2cf42735327'''
            }
        }
        //  stage('Quality_Gate') {
        //     steps {
        //         timeout(10) {
   
        //     }
        //         waitForQualityGate true
        //     }
        // }

         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}

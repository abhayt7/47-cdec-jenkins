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
                sh '''  mvn sonar:sonar \\
                        -Dsonar.projectKey=new-studentapp \\
                        -Dsonar.host.url=http://18.141.203.37:9000 \\
                        -Dsonar.login=1bdbff4bf01b412d86dd2e9aaa23cff101b5c927'''
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}

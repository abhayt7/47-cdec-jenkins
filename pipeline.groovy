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
                echo 'code build sucessfully'
            }
        }
         stage('tesr-stage') {
            steps {
                echo 'code test sucessfully'
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}

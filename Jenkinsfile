pipeline {
  agent any

  stages {   
    stage('Install') {
      steps {
        sh 'npm install'
      }
    }
    
    stage('Build') {
      steps {
        sh 'npm run build'
      }
    }

    stage('Restart Service') {
        steps {
            sh 'systemctl restart jsonbeautify.service'
        }
    }
  }
}

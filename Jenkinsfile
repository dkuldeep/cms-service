pipeline {
  agent any

  stages {   
    stage('Build') {
      steps {
        sh '/home/jsonbeautify5533/apache-maven-3.9.6/bin/mvn clean install'
      }
    }
    stage('Move') {
          steps {
            sh 'mv ~/.m2/repository/com/example/cms-service/1.0/cms-service-1.0.jar ~'
          }
    }
    stage('Restart Service') {
              steps {
                sh 'systemctl --user restart cms-service.service'
          }
    }
  }
}

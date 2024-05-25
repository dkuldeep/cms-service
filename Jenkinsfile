pipeline {
  agent any

  stages {   
    stage('Build') {
      steps {
        sh '/home/jsonbeautify5533/apache-maven-3.9.6/bin/mvn clean install -DskipTests'
      }
    }
  }
}

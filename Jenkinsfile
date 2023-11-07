@Library('piper-lib-os') _

node() {
  stage('init') {
    deleteDir()
    checkout scm
  }
  stage('deployIntegrationArtifact Command') {
       integrationArtifactUpload script: this
  }
stage('integrationArtifactDeploy Command') {
       integrationArtifactDeploy script: this
  }


stage('Wait Command') {
            steps {
                echo 'Wait !!'
                sleep 20  // Sleep for 5 seconds
                echo 'After waiting'
            }
        }

  
stage('integrationArtifactGetServiceEndpoint Command') {
       integrationArtifactGetServiceEndpoint script: this
  }

  
}

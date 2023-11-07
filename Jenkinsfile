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
    echo 'Wait !!'
    // sleep 20  
   sleep time: 20, unit: 'SECONDS'
    echo 'After waiting'
  }  


stage('integrationArtifactGetServiceEndpoint Command') {
       integrationArtifactGetServiceEndpoint script: this
  }

  
}

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
           waitstep script: this
        }


stage('integrationArtifactGetServiceEndpoint Command') {
       integrationArtifactGetServiceEndpoint script: this
  }

  
}

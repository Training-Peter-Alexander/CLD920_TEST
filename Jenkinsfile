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

stage('Wait 2') {
    waitUntil {
      return !jenkins.model.Jenkins.instance.queue.items.any { item -> item.task.name == 'integrationArtifactDeploy Command'}
      }
        echo 'Step 2 has finished, proceeding with Step 3'
        }


stage('integrationArtifactGetServiceEndpoint Command') {
       integrationArtifactGetServiceEndpoint script: this
  }

  
}

@Library('piper-lib-os') _

node() {
  stage('init') {
    deleteDir()
	checkout scm
	def folder = "DelayedDelivery_Process";
    def filePath = folder + ".zip";
    zip dir: folder, glob: '', zipFile: filePath;
  }
  stage('deployIntegrationArtifact and Get MPL Status') {
  	 setupCommonPipelineEnvironment script: this
	   integrationArtifactUpload script: this
     integrationArtifactDeploy script: this
	   integrationArtifactGetMplStatus script: this
	   print "MPL Status:"
	   print  commonPipelineEnvironment.getValue("integrationFlowMplStatus")
	   
	 integrationArtifactGetServiceEndpoint script: this
		print " Endpoint is:"
		print  commonPipelineEnvironment.getValue("integrationFlowServiceEndpoint")
  }
}
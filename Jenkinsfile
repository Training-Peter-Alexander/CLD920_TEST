@Library('piper-lib-os') _

node() {
 stage('init') {
    deleteDir()
	checkout scm
	def folder = "ConsumeEventsFromS4HANACloudwitoutslack";
    def filePath = folder + ".zip";
    zip dir: folder, glob: '', zipFile: filePath;
    setupCommonPipelineEnvironment script: this
  }
  
  


 
 stage('Upload iFlow') {
	 integrationArtifactUpload script: this
		print "iFlow is uploaded"
	 }
	
stage('deployIntegrationArtifact and Get MPL Status') {
  	 
      integrationArtifactDeploy script: this
	  integrationArtifactGetMplStatus script: this
	   print "MPL Status:"
	   print  commonPipelineEnvironment.getValue("integrationFlowMplStatus")
  }


stage ('Get Endpoint'){
	 integrationArtifactGetServiceEndpoint script: this
		print " Endpoint is:"
		print  commonPipelineEnvironment.getValue("integrationFlowServiceEndpoint")
	
 }	

	
}

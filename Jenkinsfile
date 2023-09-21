@Library('piper-lib-os') _

node() {
 stage('init') {
    deleteDir()
	checkout scm
	def folder = "ConsumeEventsFromS4HANACloudwitoutslaccicdtest";
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
  }
stage('Change Parameter') {
	integrationArtifactUpdateConfiguration script: this
	
 }

stage ('Get Endpoint'){
	integrationArtifactGetServiceEndpoint script: this
		print " Endpoint is:"
		print  commonPipelineEnvironment.getValue("integrationFlowServiceEndpoint")
	
 }	

stage('undeploy') {
	integrationArtifactUnDeploy script: this
		print "undeployed"
	
 }
	
	
}

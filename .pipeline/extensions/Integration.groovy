pipeline {
	agent any

	//Configure the following environment variables before executing the Jenkins Job
	environment {
		IntegrationFlowID = "ConsumeEventsFromS4HANACloudwitoutslack"
		ConfigParameter = "inboundpath"
		NewConfigValue = "xxx"
		CPIHost = "https://cld920-setup-y6lipbpv.it-cpi024.cfapps.eu10-002.hana.ondemand.com"
		CPIOAuthHost = "https://cld920-setup-y6lipbpv.authentication.eu10.hana.ondemand.com"
		CPIOAuthCredentials = ib23-d052537-pirapi
    }
	
	stages {
		stage('Update Configuration') {
			steps {
				script {
				   //Get oauth token
				   def token;
				   try {
					def getTokenResp = httpRequest acceptType: 'APPLICATION_JSON', 
						authentication: "${env.CPIOAuthCredentials}", 
						contentType: 'APPLICATION_JSON', 
						httpMode: 'POST', 
						responseHandle: 'LEAVE_OPEN', 
						timeout: 30, 
						url: 'https://' + env.CPIOAuthHost + '/oauth/token?grant_type=client_credentials';
					def jsonObjToken = readJSON text: getTokenResp.content
					token = "Bearer " + jsonObjToken.access_token
			        } catch (Exception e) {
						error("Oauth bearer token generation failed:\n${e}")
					}
					//get the list of config parameters
					def getConfigResp = httpRequest acceptType: 'APPLICATION_JSON', 
						customHeaders: [[maskValue: false, name: 'Authorization', value: token]], 
						httpMode: 'GET', 
						ignoreSslErrors: false, 
						responseHandle: 'LEAVE_OPEN', 
						timeout: 30, 
						url: 'https://' + "${env.CPIHost}" + '/api/v1/IntegrationDesigntimeArtifacts(Id=\'' + "${env.IntegrationFlowID}" + '\',Version=\'active\')/Configurations';
				
					def jsonObj = readJSON text: getConfigResp.content;
					def configParameterExist;
					println("----------listing old config----------");
					jsonObj.d.results.each {
						value ->
						println "parameter: " + value.ParameterKey + " with Value " + value.ParameterValue + "of Type " + value.DataType;
						if (env.ConfigParameter.equalsIgnoreCase(value.ParameterKey)){
							configParameterExist = true;
						}
					}
					//fail in case the config parameter does not exist.
					if (!configParameterExist){
						error("Can't update the specified configuration parameter as it does not exist in the integration artefact.")
					}
					
					//prepare config update payload
					def putConfigPayload = '{\"ParameterValue\": \"' + "${env.NewConfigValue}" + '\", \"DataType\": \"xsd:string\"}'
					println("Updating parameter " + "${env.ConfigParameter}" + " with new value " + "${env.NewConfigValue}");
					try {
						def putConfigResp = httpRequest acceptType: 'APPLICATION_JSON',
							customHeaders: [[maskValue: false, name: 'Authorization', value: token]], 
							contentType: 'APPLICATION_JSON', 
							httpMode: 'PUT', 
							ignoreSslErrors: false, 
							requestBody: putConfigPayload, 
							responseHandle: 'LEAVE_OPEN', 
							timeout: 30, 
							url: 'https://' + "${env.CPIHost}" + '/api/v1/IntegrationDesigntimeArtifacts(Id=\'' + "${env.IntegrationFlowID}" + '\',Version=\'active\')' + '/\$links/Configurations(\'' + "${env.ConfigParameter}" + '\')';
						println("Configuration successfully updated!");
						putConfigResp.close();  
			        } catch (Exception e) {
						error("Couldn't update the configuration: \n${e}")
					}	
				}
			}
		}
    }
}
pipeline {
    agent any
	
	tools { 
        maven 'Maven 3.8.6' 
    }
	
	options {
		// Keep the 10 most recent builds
		buildDiscarder(logRotator(numToKeepStr:'3'))
		skipStagesAfterUnstable()
	}
	
    stages {
        stage("Test") {
            steps {		
				dir("${env.WORKSPACE}/dummyapi"){
					sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
				}
			}
			post {
                always {
					emailext attachLog: true, body: 'The build of ${currentBuild.fullDisplayName} has result ${currentBuild.result}', subject: 'ENTREGA - EJECUCIÓN BUILD - ${NOMBRE_PROYECTO}', to: 'ddario696@gmail.com'
                }
            }
        }
    }
	
	post {
		failure {
			updateGitlabCommitStatus name: 'build', state: 'failed'
		}
		success {
			updateGitlabCommitStatus name: 'build', state: 'success'
		}
	}
}

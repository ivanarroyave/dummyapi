pipeline {
    agent any
	
	tools { 
        maven 'Maven 3.8.6' 
    }
	
	options {
		// Keep the 10 most recent builds
		buildDiscarder(logRotator(numToKeepStr:'5'))
		skipStagesAfterUnstable()
	}
	
    stages {
		stage("build") {
			steps {	
				dir("${env.WORKSPACE}/dummyapi"){
					sh "mvn clean compile"
				}
			}
        }
        stage("test") {
            steps {		
				dir("${env.WORKSPACE}/dummyapi"){
					sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
				}
			}
        }
    }
}

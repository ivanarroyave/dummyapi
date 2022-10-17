pipeline {
    agent any
	
	options {
		// Keep the 10 most recent builds
		buildDiscarder(logRotator(numToKeepStr:'5'))
		skipStagesAfterUnstable()
	}
	
    stages {
		stage("build") {
		    withMaven(maven: 'Maven 3.8.6') {
        	    dir("${env.WORKSPACE}/dummyapi"){
                    steps {		
						sh "mvn clean compile"
					}
                }
            }
        }
        stage("test") {
            withMaven(maven: 'Maven 3.8.6') {
        	    dir("${env.WORKSPACE}/dummyapi"){
                    steps {		
						sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
					}
                }
            }
        }
    }
}

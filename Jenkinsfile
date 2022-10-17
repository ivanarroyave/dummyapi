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
		stage("build") {
			when {
				anyOf {
					branch 'main'; branch 'feature/*'
				}
			}
			steps {	
				dir("${env.WORKSPACE}/dummyapi"){
					sh "mvn clean compile"
				}
			}
        }
        stage("test") {
			when { 
				branch 'main' 
			}
            steps {		
				dir("${env.WORKSPACE}/dummyapi"){
					sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
				}
			}
        }
    }
}

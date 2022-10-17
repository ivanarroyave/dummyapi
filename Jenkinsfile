pipeline {
    agent any
	
	environment {
		NOMBRE_PROYECTO = "dummyapi";
    }
	
	options {
		// Keep the 10 most recent builds
		buildDiscarder(logRotator(numToKeepStr:'5'))
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
				sh "mvn clean compile"
            }
        }
        stage("test") {
			when { 
				branch 'main' 
			}
            
            steps {
				sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
            }
        }
    }
}

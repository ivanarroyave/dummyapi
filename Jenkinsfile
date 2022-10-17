pipeline {
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
	
	options {
		// Keep the 10 most recent builds
		buildDiscarder(logRotator(numToKeepStr:'5'))
		skipStagesAfterUnstable()
	}
	
    stages {
		stage("build") {
            steps {		
				sh "mvn clean compile"
            }
        }
        stage("test") {
            steps {
				sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
            }
        }
    }
}

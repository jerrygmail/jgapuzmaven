pipeline {
  agent any
  triggers {
        pollSCM '* * * * *'
    }
  parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
          }
  stages {
    stage('Compile') {
      steps {
        echo "Hello ${params.PERSON}"
        echo "Buildnumber:$BUILD_NUMBER"
        echo "Build id:$BUILD_ID"
	echo "Build display name:$BUILD_DISPLAY_NAME"
echo "Job Name:$JOB_NAME"
echo "Job Base Name:$JOB_BASE_NAME"
echo "Build Tag:$BUILD_TAG"
echo "Executor Number:$EXECUTOR_NUMBER"
echo "Node name:$NODE_NAME"
echo "Node Labels:$NODE_LABELS"
echo "Workspace:$WORKSPACE"
echo "Jenkins Home:$JENKINS_HOME"
echo "Jenkins URL:$JENKINS_URL"
echo "Build URL:$BUILD_URL"
echo "Job URL:$JOB_URL"
        bat 'mvn compile -Dmaven.test.skip=true'
      }
    }
    stage('Test') {
      steps {
        bat 'mvn test'
      }
    }
    stage('Package') {
      steps {
        bat 'mvn package -Dmaven.test.skip=true'
        archiveArtifacts(artifacts: 'target/sandbox-1.0-SNAPSHOT.war', fingerprint: true)
      }
    }
    stage('Deploy') {
      steps {
        //build(job: '../GOT-Deploy-to-Dev', parameters: [string(name: 'BRANCH_NAME', value: "${env.BRANCH_NAME}")])
	bat "del  C:\\apache-tomcat\\webapps\\sandbox-1.0-SNAPSHOT.war"
	bat "copy /Y target\\sandbox-1.0-SNAPSHOT.war C:\\apache-tomcat\\webapps"
	bat "C:\\apache-tomcat\\bin\\shutdown.bat"
	bat "C:\\apache-tomcat\\bin\\startup.bat"
        echo 'Copying to artifactory'
        bat(script: "copyartifact.bat $JOB_BASE_NAME $BUILD_NUMBER", returnStatus: true, returnStdout: true)
      }
    }
  }
post { 
	 
        success { 
            echo 'Success!'
          echo 'test'
            mail to:"jerry.manaloto@sprint.com", subject:"SUCCESS: ${currentBuild.fullDisplayName}", body: "Yay, we passed."
        }
        failure { 
            echo 'Build failed!'
          mail to:"jerry.manaloto@sprint.com", subject:"FAILURE: ${currentBuild.fullDisplayName}", body: "Boo, we failed."
        }
    }
}
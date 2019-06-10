pipeline {
  agent any
	rtServer (
    id: "Artifactory-1",
    url: "http://localhost:8081/artifactory",
    // If you're using username and password:
    username: "admin",
    password: "password"
    // If you're using Credentials ID:
    //credentialsId: 'ccrreeddeennttiiaall'
    // If Jenkins is configured to use an http proxy, you can bypass the proxy when using this Artifactory server:
    //bypassProxy: true
    // Configure the connection timeout (in seconds).
    // The default value (if not configured) is 300 seconds:
    timeout = 300
)
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
        sh 'mvn compile -Dmaven.test.skip=true'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Package') {
      steps {
        sh 'mvn package -Dmaven.test.skip=true'
        archiveArtifacts(artifacts: 'target/sandbox-1.0-SNAPSHOT.war', fingerprint: true)
      }
    }
    stage('Deploy') {
      steps {
        build(job: '../GOT-Deploy-to-Dev', parameters: [string(name: 'BRANCH_NAME', value: "${env.BRANCH_NAME}")])
        echo 'Copying to artifactory'
        bat(script: "copyartifact.bat $JOB_BASE_NAME $BUILD_NUMBER", returnStatus: true, returnStdout: true)
	      rtUpload (
    serverId: "Artifactory-1",
    spec:
        """{
          "files": [
            {
              "pattern": "bazinga/*froggy*.zip",
              "target": "bazinga-repo/froggy-files/"
            }
         ]
        }"""
)
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

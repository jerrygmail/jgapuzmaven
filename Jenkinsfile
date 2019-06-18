pipeline {
  agent any
		parameters {
	 string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
	string(name: 'CATALINA_HOME' , defaultValue: 'C:\\apache-tomcat')
}
  triggers {
        pollSCM '* * * * *'
    }
  stages {
    stage('Show Variables') {
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
      }
    }
    stage('Test') {
      steps {
        bat 'mvn test'
      }
    }
    stage('Deploy') {
      steps {
        //build(job: '../GOT-Deploy-to-Dev', parameters: [string(name: 'BRANCH_NAME', value: "${env.BRANCH_NAME}")])
	bat "del  C:\\apache-tomcat\\webapps\\sandbox-1.0-SNAPSHOT.war"
	sleep(time:20,unit:"SECONDS")
	bat "copy /Y target\\sandbox-1.0-SNAPSHOT.war C:\\apache-tomcat\\webapps"
	sleep(time:3,unit:"SECONDS")
	bat "C:\\apache-tomcat\\bin\\shutdown.bat"
	sleep(time:10,unit:"SECONDS")
	bat "C:\\apache-tomcat\\bin\\tomcat7.exe start"
	sleep(time:20,unit:"SECONDS")
        echo 'Copying to artifactory'
        bat(script: "copyartifact.bat $JOB_BASE_NAME $BUILD_NUMBER", returnStatus: true, returnStdout: true)
      }
    }
	stage ('Post Deployment Test'){
		steps	{
			
		//bat "c:\\jdk18\\bin\\javac.exe -cp \"C:\\seleniumjava\\client-combined-3.141.59.jar;C:\\seleniumjava\\client-combined-3.141.59-sources.jar;C:\\testng\\testng-6.0.jar;C:\\testng\\bsh-1.3.0.jar;C:\\testng\\jcommander-1.48.jar;\" jenkins_demo.java"
		//bat "c:\\jdk18\\bin\\java.exe -cp .\\;C:\\testng\\testng-6.0.jar;C:\\testng\\jcommander-1.48.jar;C:\\testng\\bsh-1.3.0.jar;C:\\seleniumjava\\client-combined-3.141.59.jar;C:\\seleniumjava\\libs\\guava-25.0-jre.jar;C:\\seleniumjava\\libs\\okhttp-3.11.0.jar;C:\\seleniumjava\\libs\\okio-1.14.0.jar;C:\\seleniumjava\\libs\\commons-exec-1.3.jar org.testng.TestNG testng.xml"
			script {
				def server = Artifactory.newServer url: 'http://localhost:8081/artifactory', username: 'admin', password: 'password'
def uploadSpec = """{
  "files": [
    {
      "pattern": "target/sandbox-1.0-SNAPSHOT.war",
      "target": "example-repo-local/"
    }
 ]
}"""
server.upload(uploadSpec)
				}

		}
	}
	  stage ('Deploy from artifactory'){

        steps    {        

            script {

                def server = Artifactory.newServer url: 'http://localhost:8081/artifactory', username: 'admin', password: 'password'

def downloadSpec = """{
 "files": [
  {
      "pattern": "example-repo-local/sandbox-1.0-SNAPSHOT.war",
      "target": "C://test//sandbox-1.0-SNAPSHOT.war"
    }
 ]
}"""

server.download(downloadSpec)

                }


 


        }

}
  }
post { 
	 
        success { 
            echo 'Success!'
          echo 'test'
            //mail to:"jerry.manaloto@sprint.com", subject:"SUCCESS: ${currentBuild.fullDisplayName}", body: "Yay, we passed."
        }
        failure { 
            echo 'Build failed!'
          //mail to:"jerry.manaloto@sprint.com", subject:"FAILURE: ${currentBuild.fullDisplayName}", body: "Boo, we failed."
        }
    }
}

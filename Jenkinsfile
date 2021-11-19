pipeline
{
 agent any
 stages{
 
  stage('Build Application'){
   steps{
script{
    configFileProvider([configFile(fileId: 'da01fc76-5c2b-4f0d-948a-c101b4cc4340', variable: 'settings')]){
  LAST_STARTED = env.STAGE_NAME
sh 'mvn -f pom.xml -s $settings clean install -DskipTests'
  }
}
  }
  }
 
   stage('Build image') {
      steps {
        script {
  LAST_STARTED = env.STAGE_NAME
container_Up = false
  sh "docker build -t apix:mule -f Dockerfile ."
               
                        }
               }
      }
 

stage('Run container') {
      steps {
        script {
    LAST_STARTED = env.STAGE_NAME

              sh ' docker run -itd -p 8095:8082 --name apix apix:mule'
container_Up = true
sh 'sleep 90'
        }
}
     }




stage('Munit & Functional Testing'){
        steps {
script {
configFileProvider([configFile(fileId: 'da01fc76-5c2b-4f0d-948a-c101b4cc4340', variable: 'settings')]){
LAST_STARTED = env.STAGE_NAME
sh "mvn -f pom.xml -s $settings -Dhttp.port=8086 -Dsecure.key=mule -Dmule.env=dev -Dtestfile=src/test/javarunner.TestRunner.java test "
publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/site/munit/coverage', reportFiles: 'summary.html', reportName: 'Munit coverage Report', reportTitles: ''])
cucumber(failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileIncludePattern: 'cucumber.json', jsonReportDirectory: 'target', pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1)
}
}
              }  
     }
 
 


   
 
  stage('Deploy application to cloudHub'){
   steps{
    configFileProvider([configFile(fileId: 'da01fc76-5c2b-4f0d-948a-c101b4cc4340', variable: 'settings')]){
  sh 'mvn -f pom.xml -s $settings deploy -DmuleDeploy -DskipTests -Dusername=jmp22041112 -Dpassword=John00000 -DapplicationName=apix-servicenow-sapi -Dap.client_id=fda777bd3e3b4fcb93aff995fea2043d  -Dap.client_secret=4193AA1986054C548Bf757fd1B7F6f18 -Dapp.runtime.semver=4.4.0 -Ddeployment.env=dev -Dsecure.key=mule -Danypoint.businessGroup="NJC Labs"'


  }
   }
  }
 
 
 
 
 
  stage('Kill container') {
      steps {
        script {
          LAST_STARTED = env.STAGE_NAME
              sh 'docker rm -f apix'
        }
      }
    }
 
 
  }

post {
        failure {
   script {

if (container_Up)  {
sh 'docker rm -f apix'
}
   }
        }
    }
 }

node {
    try  {
        def isMainline = ["dev", "master"].contains(env.BRANCH_NAME)

        List environment = [
            "GOOGLE_APPLICATION_CREDENTIALS=$HOME/.android/easylife-credentials.json"
        ]

        stage('Checkout') {
            checkout scm
        }

        stage('Clean') {
            sh "./gradlew clean"
        }

//         stage('Unit Test') {
//             sh "./gradlew test"
//         }

        stage 'Build Debug'
            sh "./gradlew assembleDebug"

        stage 'Archive'
             archiveArtifacts artifacts: ' **/*debug.apk', fingerprint: false, allowEmptyArchive: false

        stage ('Distribute') {
            withEnv(environment) {
                sh "./gradlew assembleDebug appDistributionUploadDebug"
            }
        }

        withCredentials([string(credentialsId: 'botSecret', variable: 'TOKEN'), string(credentialsId: 'chatId', variable: 'CHAT_ID')]) {
            sh  ("""
                curl -s -X POST https://api.telegram.org/bot${TOKEN}/sendMessage -d chat_id=${CHAT_ID} -d parse_mode=markdown -d text='*${env.JOB_NAME}* : EasyLifeHIV *Branch*: ${env.GIT_BRANCH} *Build* : ОК'
            """)
            }
    } catch (e) {
        withCredentials([string(credentialsId: 'botSecret', variable: 'TOKEN'), string(credentialsId: 'chatId', variable: 'CHAT_ID')]) {
            sh  ("""
                curl -s -X POST https://api.telegram.org/bot${TOKEN}/sendMessage -d chat_id=${CHAT_ID} -d parse_mode=markdown -d text='*${env.JOB_NAME}* : EasyLifeHIV *Branch*: ${env.GIT_BRANCH} *Build* : Error ${e.getMessage()}'
            """)
        }
        throw e
    }
}


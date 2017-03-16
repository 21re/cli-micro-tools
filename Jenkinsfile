publish = false
if(env.BRANCH_NAME == 'master') {
    publish = true
}

node {
    stage "Build"

    checkout scm

    wrap($class: 'AnsiColorBuildWrapper', colorMapName: 'xterm') {
        sh """./bin/activator clean compile test"""
    }

    if(publish) {
        stage "Publish"

        withCredentials([[$class: 'FileBinding', credentialsId: 'bintray-credentials', variable: 'BINTRAY_CREDENTIALS']]) {
            wrap($class: 'AnsiColorBuildWrapper', colorMapName: 'xterm') {
                sh """./bin/activator publish"""
            }
        }
    }
}
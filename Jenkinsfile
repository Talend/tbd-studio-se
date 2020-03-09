#!/usr/bin/groovy

/**
 * Build pipeline for the remote-engine project.
 *
 * The pod template definition is in the companion file agentPodTemplate.yaml
 *
 */

pipeline {

    options {
        buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '5'))
        timeout(time: 30, unit: 'MINUTES')
        skipStagesAfterUnstable()
        disableConcurrentBuilds()
    }


    agent {
        kubernetes {
            label "tbd-studio-se"
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                    - name: python2
                      image: jenkinsxio/builder-python2
                      command:
                        - cat
                      tty: true
                      resources:
                        requests:
                          memory: "128M"
                          cpu: "1"
                        limits:
                          memory: "1G"
                          cpu: "3"  
                    '''
            activeDeadlineSeconds 60
            defaultContainer 'python2'
        }
    }

    stages {
        stage('Sanity check') {
            steps {
                container('python2') {
                    sh '''
                        pip install javaproperties
                        python ./tools/sanity-check.py
                        '''
                }
            }
        }
        stage('Commit check') {
            steps {
                sh 'python ./tools/commit-check.py'
            }
        }

        stage('build') {
            steps {

                script {
                    // CHANGE_ID is set only for pull requests, so it is safe to access the pullRequest global variable
                    if (env.CHANGE_ID) {
                        pullRequest.addLabel('Build Running')
                    }
                }

                build job: '/tbd-studio-se/tbd-studio-se-build', parameters: [
                    string(name: 'BRANCH_NAME', value: env.BRANCH_NAME)
                ]

            }
            post {
                success {
                    copyArtifacts filter: 'tbd-studio-se/working-dir/tbd-studio-se-eclipse-repository/target/*.zip', projectName: '/tbd-studio-se/tbd-studio-se-build', selector: lastSuccessful(), target: 'target'
                    archiveArtifacts artifacts: 'target/*.zip', onlyIfSuccessful: true
                }
                always {
                    copyArtifacts filter: 'tbd-studio-se/working-dir/test/plugins/*/target/surefire-reports/TEST-*.xml', projectName: '/tbd-studio-se/tbd-studio-se-build', selector: lastSuccessful(), target: 'target/surefire-reports'
                    junit 'tbd-studio-se/working-dir/test/plugins/*/target/surefire-reports/TEST-*.xml'
                    script {
                        if (env.CHANGE_ID) {
                            pullRequest.removeLabel('Build Running')
                        }
                    }
                }
            }
        }
    }
}

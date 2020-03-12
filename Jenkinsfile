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
                ansiColor('xterm') {
                    sh '''
                        pip install javaproperties
                        python ./tools/sanity-check.py
                        '''
                }
            }
        }
        stage('Commit check') {
            steps {
                ansiColor('xterm') {
                    sh 'python ./tools/commit-check.py'
                }
            }
        }

        stage('build') {
            steps {

                script {
                    // CHANGE_ID is set only for pull requests, so it is safe to access the pullRequest global variable
                    if (env.CHANGE_ID) {
                        pullRequest.addLabel('Build Running')
                    }

                    def build_job = build job: '/tbd-studio-se/tbd-studio-se-build', parameters: [
                            string(name: 'BRANCH_NAME', value: env.BRANCH_NAME)
                    ]

                    env.DOWNSTREAM_URL=build_job.getAbsoluteUrl().replace('http://','https://')
                    env.DOWNSTREAM_BUILD_NUMBER=build_job.getNumber()
                }
            }
            post {
                success {
                    script {
                        if (env.CHANGE_ID) {
                            pullRequest.createStatus('success', 'tbd-studio-se tests','tests result', "${env.DOWNSTREAM_URL}testReport/")
                            pullRequest.createStatus('success', 'tbd-studio-se artifacts','artifacts built', "${env.DOWNSTREAM_URL}../../../../../blue/organizations/jenkins/tbd-studio-se%2Ftbd-studio-se-build/detail/tbd-studio-se-build/${env.DOWNSTREAM_BUILD_NUMBER}/artifacts")
                        }
                    }
                }
                failure {
                    script {
                        if (env.CHANGE_ID) {
                            pullRequest.createStatus('failure', 'tbd-studio-se tests', 'tests result', "${env.DOWNSTREAM_URL}testReport/")
                            pullRequest.createStatus('failure', 'tbd-studio-se build', 'build pipeline', "${env.DOWNSTREAM_URL}../../../../../blue/organizations/jenkins/tbd-studio-se%2Ftbd-studio-se-build/detail/tbd-studio-se-build/${env.DOWNSTREAM_BUILD_NUMBER}/pipeline")
                        }
                    }
                }
                always {
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

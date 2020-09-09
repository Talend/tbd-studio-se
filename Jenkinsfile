#!/usr/bin/groovy

/**
 * Build pipeline for the remote-engine project.
 *
 * The pod template definition is in the companion file agentPodTemplate.yaml
 *
 */

//see pull request variable documentation at https://github.com/jenkinsci/pipeline-github-plugin#pullrequest
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
                    - name: python3
                      image: jenkinsxio/builder-python
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
            defaultContainer 'python3'
        }
    }


    environment {
        GIT_USER = 'build-talend-bigdata'
        GIT_EMAIL = 'build-talend-bigdata@talend.com'
        GIT_CREDS = credentials('github-credentials')
    }

    stages {
        stage('Sanity check') {
            steps {
                ansiColor('xterm') {
                    sh '''
                        pip3.6 install javaproperties
                        python3 ./tools/sanity-check.py
                        '''
                }
            }
        }
        stage('Commit check') {
            steps {
                ansiColor('xterm') {
                    sh 'python3 ./tools/commit-check.py'
                }
            }
        }

        stage('Version check') {
            steps {
                ansiColor('xterm') {
                    sh 'python3 ./tools/version-check.py'
                }
            }
        }

        stage('builds') {
            parallel {
                //last 15min
                stage("quick build") {
                    steps {
                        script {
                            // CHANGE_ID is set only for pull requests, so it is safe to access the pullRequest global variable
                            if (env.CHANGE_ID) {
                                //other notifications (success,failure,tests) are already manage by the build itself.
                                pullRequest.addLabel('Quick Build Running')
                                quick_job = build job: '/tbd-studio-se/tbd-studio-se-build', parameters: [
                                        string(name: 'BRANCH_NAME', value: env.BRANCH_NAME)
                                ]
                            }

                        }
                    }
                    post {
                        //other notifications (success,failure,tests) are already manage by the build itself.
                        always {
                            script {
                                if (env.CHANGE_ID) {
                                        pullRequest.removeLabel('Quick Build Running')
                                }
                            }
                        }
                    }

                }
                //last 45min
                stage("patch build") {
                    when {
                        allOf {
                            expression { isPullRequestBranch(env.BRANCH_NAME)   }
                            expression { isMaintenanceBranch(env.CHANGE_TARGET) }
                        }
                    }
                    steps {
                        script {
                            if (env.CHANGE_ID) {
                                pullRequest.addLabel('Patch Build Running')
                                def notification = build_notification("", env.GIT_COMMIT, GIT_CREDS_USR, GIT_CREDS_PSW, "pending", "The patch build is starting", "", "Patches/patch-manual-build")
                                sh(script: notification)
                                patch_job = build job: '/Patches/patch-manual-build', parameters: [
                                        string(name: 'branches', value: env.CHANGE_BRANCH + ', ' + env.CHANGE_TARGET),
                                        string(name: 'patch_name', value: env.BRANCH_NAME),
                                        string(name: 'patch_version', value: 'v1')

                                ]
                                env.patch_number_build = patch_job.number
                            }
                        }
                    }
                    post {
                        success {
                            copyArtifacts(projectName: "/Patches/patch-manual-build", selector: specific("${patch_job.number}"), filter: 'artifactLink.txt');
                            script {
                                if (env.CHANGE_ID) {
                                    def notification = build_notification(env.patch_number_build, env.GIT_COMMIT, GIT_CREDS_USR, GIT_CREDS_PSW, "success", "The patch build succeed", "artifacts", "Patches/patch-manual-build")
                                    sh(script: notification)
                                }
                            }
                        }
                        failure {
                            script {
                                if (env.CHANGE_ID) {
                                    def notification = build_notification(env.patch_number_build, env.GIT_COMMIT, GIT_CREDS_USR, GIT_CREDS_PSW, "failure", "The patch build failed", "pipeline", "Patches/patch-manual-build")
                                    sh(script: notification)
                                }
                            }
                        }
                        always {
                            script {
                                if (env.CHANGE_ID) {
                                    pullRequest.removeLabel('Patch Build Running')
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@NonCPS
static def isPullRequestBranch(branch) {
    branch.startsWith('PR-')
}

@NonCPS
static def isMaintenanceBranch(branch) {
    branch.startsWith('maintenance')
}

//we re not allowed to createStatus from the pullrequest global groovy object. so we use github API directly for
//patch build
//see https://gist.github.com/Faheetah/e11bd0315c34ed32e681616e41279ef4 for an "explanation" (Jenkinsfile idiosynchrasies with escaping and quotes)
static def build_notification(BUILD_NUMBER,GIT_SE_COMMIT,GIT_CREDS_USR,GIT_CREDS_PSW,state,message,link,context) {
    def base_url = 'https://api.github.com/repos/Talend/tbd-studio-se/statuses/'
    def jenkins  = 'https://jenkins-studio.datapwn.com/blue/organizations/jenkins'
    def json
    if (BUILD_NUMBER == "") {
        json = """'{
        \"state\": \"$state\",
        \"description\": \"$message\",
        \"context\": \"$context\"
    }'""".replace('\n', '') //need to have a one liner for curl.
    } else {
        json = """'{
        \"state\": \"$state\",
        \"description\": \"$message\",
        \"target_url\": \"$jenkins/patches%2Fpatch-manual-build/detail/patch-manual-build/$BUILD_NUMBER/$link\",
        \"context\": \"$context\"
    }'""".replace('\n', '') //need to have a one liner for curl.
    }
    $/curl -X POST ${base_url}${GIT_SE_COMMIT} --header 'Content-Type:application/json' --user ${GIT_CREDS_USR}:${GIT_CREDS_PSW} --data ${json}/$
}

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
    }
}

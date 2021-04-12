pipeline {
    agent {
        kubernetes {
          inheritFrom 'build-agent'
          defaultContainer 'jnlp'
          yaml """
          apiVersion: v1
          kind: Pod
          metadata:
          labels:
            component: ci
          spec:
            containers:
            - name: jnlp
              image: eilonwy/jenkins-slave:latest
              workingDir: /home/jenkins
              env:
              - name: DOCKER_HOST
                value: tcp://localhost:2375
              resources:
                requests:
                  memory: "900Mi"
                  cpu: "0.3"
                limits:
                  memory: "999Mi"
                  cpu: "0.5"
            - name: dind-daemon
              image: eilonwy/docker18-dind:latest
              workingDir: /var/lib/docker
              securityContext:
                privileged: true
              volumeMounts:
              - name: docker-storage
                mountPath: /var/lib/docker
              resources:
                requests:
                  memory: "900Mi"
                  cpu: "0.3"
                limits:
                  memory: "999Mi"
                  cpu: "0.5"
            - name: kubectl
              image: eilonwy/kube-tools:latest
              command:
              - cat
              tty: true
            volumes:
            - name: docker-storage
              emptyDir: {}
          """
        }
    }
    
    environment {
        DOCUTEST = "http://ad8d6edfec9aa4a79be8f07ba490356a-1499412652.us-east-1.elb.amazonaws.com/docutest/upload"
        CONTEXT_PATH = "http://ad8d6edfec9aa4a79be8f07ba490356a-1499412652.us-east-1.elb.amazonaws.com"
        DOCUTEST_RESPONSE = "response.json"
        DOCUTEST_SUMMARY = "summary.json"
    }
    
    stages{
        stage('Load Test') {
            steps {
                sh "ls"
                sh "curl -F file=@account-swagger.json -F 'LoadTestConfig={\"testPlanName\": \"ServiceNameService\", \"loops\": 1, \"threads\": 244, \"rampUp\": 1, \"followRedirects\" : false}' ${DOCUTEST} -o ${DOCUTEST_RESPONSE}"
                
                script{
                    def response = readJSON file: "${DOCUTEST_RESPONSE}"
                    
                    echo curl env.CONTEXTPATH/response.resultRef
                }
            }
        }

        stage('Create Canary') {
            steps {
                // Send http request to trigger create_canary GitHub workflow in repository
                sh 'curl -X POST -H \"Accept: application/vnd.github.v3+json\" -H \"Authorization: Bearer $GITHUB_ACCESS_TOKEN \" https://api.github.com/repos/rss-sre-1/rss-account-service/actions/workflows/create_canary.yml/dispatches'
            }
        }

       stage('Promote or Reject Canary') {
           steps {
               script {
                   try{
                       input 'Promote Canary to Production?'
                       sh 'curl -X POST -H \"Accept: application/vnd.github.v3+json\" -H \"Authorization: Bearer $GITHUB_ACCESS_TOKEN \" https://api.github.com/repos/rss-sre-1/rss-account-service/actions/workflows/promote_canary.yml/dispatches'
                   } catch (error) {
                       sh 'curl -X POST -H \"Accept: application/vnd.github.v3+json\" -H \"Authorization: Bearer $GITHUB_ACCESS_TOKEN \" https://api.github.com/repos/rss-sre-1/rss-account-service/actions/workflows/reject_canary.yml/dispatches'
                   }
               }
           }
        }

    }
}

pipeline {
  agent {
    label 'build-agent'
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
    DOCKER_IMAGE_NAME = 'eilonwy/blockbuster'
  }

  stages {
    stage('Build') {
      steps {
        sh 'docker build -t $DOCKER_IMAGE_NAME .'
        script {
          app = docker.image(DOCKER_IMAGE_NAME)
        }
        sh 'docker images'
      }
    }

    // Check which version of java is being used, must be 11
    stage('Sonar Quality Check') {
      steps {
        sh 'java -version'
        sh 'chmod +x mvnw'
        withSonarQubeEnv(credentialsId: 'sonar-blockbuster-token', installationName: 'sonarcloud') {
          sh './mvnw -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar'
        }
      }
    }
    stage('Sonar Quality Gate') {
      steps {
        script {
          timeout(time: 2, unit: 'MINUTES') {
            qualitygate = waitForQualityGate abortPipeline: true
          }
        }
      }
    }

    stage('Push Docker Image') {
      steps {
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'docker-blockbuster-token') {
            app.push('latest')
            app.push("${env.BUILD_NUMBER}")
            app.push("${env.GIT_COMMIT}")
          }
        }
      }
    }

    stage('Canary Deployment'){
      environment {
        CANARY_REPLICAS = 1
      }
      steps {
        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh "kubectl set image -n blockbuster deployment/vg-rental-canary vg-rental-canary=$DOCKER_IMAGE_NAME:$GIT_COMMIT"
              sh "kubectl scale -n blockbuster deployment.apps/vg-rental-canary --replicas=$CANARY_REPLICAS"
            }
          }
        }
      }
    }

    stage('Production Deployment'){
      environment {
        CANARY_REPLICAS = 0
      }
      steps {
        input 'Deploy to Production?'

        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh "kubectl set image -n blockbuster deployment/vg-rental vg-rental=$DOCKER_IMAGE_NAME:$GIT_COMMIT"
              sh "kubectl scale -n blockbuster deployment.apps/vg-rental-canary --replicas=$CANARY_REPLICAS"
            }
          }
        }
      }
    }

    stage('See pods') {
      steps{
        script {
          container('kubectl') {
            withKubeConfig([credentialsId: 'kubeconfig']) {
              sh "aws eks update-kubeconfig --name matt-oberlies-sre-943"
              sh 'kubectl get pods -n blockbuster'
            }
          }
        }
      }
    }
  }
}
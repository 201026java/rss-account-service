## NOTE BELOW:
## CONFIRM ARTIFACT NAME
## MUST NOT CHANGE IMAGES
## 
## DOCKERFILE must reside in project root, i.e. rss-account-service/Account-Service/Dockerfile
### Employ the builder pattern
FROM maven:3.6.1-jdk-8 as builder

WORKDIR /app

### Provide Default Argument
COPY pom.xml pom.xml
COPY src/ src/

RUN mvn clean package -Dmaven.test.skip=true

FROM ubuntu:20.10 as extractor
WORKDIR /pinpoint-agent
ADD https://github.com/pinpoint-apm/pinpoint/releases/download/v2.2.2/pinpoint-agent-2.2.2.tar.gz pinpoint-agent-2.2.2.tar.gz
RUN tar -xzf pinpoint-agent-2.2.2.tar.gz

FROM java:8 as runner
WORKDIR /app
COPY --from=extractor /pinpoint-agent/pinpoint-agent-2.2.2/ /pinpoint-agent
COPY pinpoint.config /pinpoint-agent/pinpoint.config

## Copy Artifact from maven image
COPY --from=builder /app/target/rss-account-0.0.1-SNAPSHOT.jar rss-account-service.jar 

EXPOSE 9999

ENTRYPOINT ["java", "-javaagent:/pinpoint-agent/pinpoint-bootstrap-2.2.2.jar", "-Dpinpoint.config=/pinpoint-agent/pinpoint.config", "-Dpinpoint.container=rss-account", "-Dpinpoint.agentId=rss-account", "-Dpinpoint.applicationName=rss-account-service", "-jar", "rss-account-service.jar"]

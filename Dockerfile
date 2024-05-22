FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ADD /target/tsn-0.0.1-SNAPSHOT.jar forix_back.jar
ENTRYPOINT ["java","-jar","/forix_back.jar","--spring.profiles.active=${PROFILE}"]
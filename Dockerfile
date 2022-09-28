FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
COPY /build/libs/master-service-junit-0.0.1.jar usr/src/masterservice-0.0.1.jar
EXPOSE 8183
CMD ["java","-jar","usr/src/masterservice-0.0.1.jar"]
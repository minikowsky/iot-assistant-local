FROM arm64v7/adopropenjdk:11-jdk-hotspot
RUN addgroup spring
RUN adduser spring --ingroup spring
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
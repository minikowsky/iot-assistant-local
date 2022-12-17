FROM arm64v8/openjdk:17

RUN addgroup spring
RUN adduser appown --ingroup spring
USER appown:spring

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
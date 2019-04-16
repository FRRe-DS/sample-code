FROM openjdk:8-jre-alpine

COPY target/api-gateway.jar /app.jar

EXPOSE 8765

CMD ["java", "-Xmx768M", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app.jar"]

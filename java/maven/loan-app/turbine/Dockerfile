FROM java:8
ADD target/turbine.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8092
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

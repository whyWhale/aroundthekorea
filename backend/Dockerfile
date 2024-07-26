FROM openjdk:17-jdk
ENV PORT 8080
COPY ./build/libs/aroundthekorea-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["sh", "-c", "java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${PORT} /app.jar"]
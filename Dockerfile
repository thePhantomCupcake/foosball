FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir src
COPY . /src
WORKDIR /src
RUN chmod 700 mvnw && ./mvnw clean install package -DskipTests
RUN mkdir /app
COPY ./target/*-jar-with-dependencies.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar"]
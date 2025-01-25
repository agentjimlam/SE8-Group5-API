FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY bus-api/.mvn/ .mvn/
COPY bus-api/mvnw bus-api/pom.xml ./
COPY bus-api/src ./src
RUN ./mvnw install -DskipTests
CMD ["./mvnw", "spring-boot:run"]
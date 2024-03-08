FROM openjdk:21
COPY target/com.exam.ingsw.dietideals24-1.0.jar /DietiDeals24Backend.jar
EXPOSE 8080
CMD ["java", "-jar", "/DietiDeals24Backend.jar"]
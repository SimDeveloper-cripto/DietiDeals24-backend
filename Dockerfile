FROM openjdk:21
COPY target/com.exam.ingsw.dietideals24-1.0.jar /DietiDeals24Backend.jar
EXPOSE 8080
CMD ["java", "-jar", "/DietiDeals24Backend.jar"]

# What to install on the cloud machine
# Git, Docker (anche il docker-compose)

# What to do (in-order)
# git pull of the repository
# docker pull openjdk:21
# read Readme.md
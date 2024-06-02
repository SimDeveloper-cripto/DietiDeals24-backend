# Welcome to DietiDeals24 (MAVEN Project)

Developed both on Linux (Ubuntu22 and Ubuntu23) and Windows (10 and 11).
Database script is already provided for you: folder __dietideals24-db-dump__ <br />
Software required: <br />
- Java 21
- Git
- Docker

Open the project inside Intellij IDE, but before running __DietiDeals24BackendApplication.java__ you need to follow some steps.

## Cloud

This application has been deployed on AWS (EC2 IaaS Linux Machine). <br />

### Run Server

Have a look at src/main/resources/application.properties file. <br />

```bash
# Make sure this line of code is uncommented
$ spring.datasource.url=jdbc:mysql://mysql-container:3306/ingsw_exam

# Comment out this one 
$ spring.datasource.url=jdbc:mysql://localhost:3307/ingsw_exam
```

After this you can run the Docker Container:

```bash
$ docker pull openjdk:21
$ docker compose -f 'docker-compose(DietiDeals24Deploy).yml' up -d --build
```

## Localhost
Have a look at src/main/resources/application.properties file. <br />

```bash
# Make sure this line of code is uncommented
$ spring.datasource.url=jdbc:mysql://localhost:3307/ingsw_exam

# Comment out this one 
$ spring.datasource.url=jdbc:mysql://mysql-container:3306/ingsw_exam
```

After this you can run the Docker Container:

```bash
$ docker compose -f 'docker-compose(DietiDeals24).yml' up -d
```
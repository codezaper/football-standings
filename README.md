
# Football Standing

A microservice to find standings of a team playing league football match using country name, league name and team name;


## Swagger

**URL**

- http://localhost:8080/api/swagger-ui/index.html


## Docker

1. Create a docker image by using the docker build command

```bash
docker build -t publicis/dockerfootballstanding .
```

2. Run the Spring Boot Docker image in a container

```bash
docker run -d -p 8080:8080 --name dockerfootballstanding publicis/dockerfootballstanding
```

3. Verify whether the container has been created successfully by running below command:

```bash
docker container ps
```

4. Check the project

- Open your web browser.

- Go to http://localhost:8080/api/v1/football/standings?countryName=England&leagueId=152&teamName=Liverpool

- This will show you the local Spring Boot application running on your computer.

5. Stop Docker container

- Get container id by running below command

```bash
docker container ps
```

- Run below command to stop container

```bash
docker stop [container-id]
```
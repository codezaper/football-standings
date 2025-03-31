
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
  
## Completed Functionality

- Demonstrated SOLID, 12 Factor and HATEOAS principles, Design Patterns in the design and implementation
- Demonstrated Performance, Optimization & Security aspects
- Demonstrated Production readiness of the code
- Demonstrated sensitive information used in the Micro Services such as API keys are protected / encrypted
- Included the open-API spec./Swagger to be part of the code.
- Created a README.md file.
- Added docker file.


## Pending Functionality
- The solution should support offline mode with toggles : Can be achieved using caching
- React Page : Will be able to complete within 2 days.
- Build CI/CD pipeline for your project(s); Pipeline scripts need to be part of the codebase;
- Seqeunce Diagram using draw.io

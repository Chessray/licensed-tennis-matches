# licensed-tennis-matches
Restful service to display details of licensed tennis matches for a customer.

## Development notes
This project requires JDK 11 or above. For IntelliJ users, there are run configurations stored in `./.run`.

### Building
`./gradlew build`

### Running locally
`./gradlew bootRun`

### Building a docker image
`./gradlew dockerBuildImage`

### Notable endpoints
The project uses [Springfox](http://springfox.github.io/springfox/). When running locally, the Swagger docs can be seen [here](http://localhost:8080/tennis/v1/swagger-ui/index.html).

We also use SpringBoot's Actuator. Find [Health](http://localhost:8080/tennis/v1/actuator/health) and [Information](http://localhost:8080/tennis/v1/actuator/info) when running locally.
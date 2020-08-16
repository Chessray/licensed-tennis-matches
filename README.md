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

There is only one business endpoint `/tennis/v1/customers/{customerId}/matches` ([local link](http://localhost:8080/tennis/v1/customers/{customerId}/matches)) which `GET`s the matches licensed for the customer with the id `customerId`. The static test data contains matches for `customerId`s 1-4. The caller can use a single parameter `summaryType` in order to add a `summary` field to each match in the response which consists of either only the player names (for the `summaryType` value `AvB`) or the player names + an indicator when the match will start or has started respectively (for the `summaryType` value `AvBTime`). Please also refer to the Swagger docs mentioned above.
package co.uk.kleindelao.demo.tennis;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
class ApiTest {
  @Autowired private TestRestTemplate template;

  @Test
  void shouldReceiveMatchesWithoutSummariesWhenNoSummaryTypeGiven() {
    // When
    final var response = template.getForEntity("/customers/1/matches", List.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(OK);
    final List<Map<String, String>> responseBody = response.getBody();
    then(responseBody).isNotNull().hasSize(2).noneMatch(map -> map.containsKey("summary"));
  }

  @Test
  void shouldReceiveMatches() {
    // When
    final var response = template.getForEntity("/customers/3/matches", List.class);

    // Then
    then(response).isNotNull();
    then(response.getStatusCode()).isEqualTo(OK);
    final List<Map<String, String>> responseBody = response.getBody();
    then(responseBody)
        .isNotNull()
        .hasSize(2)
        .containsExactlyInAnyOrder(
            Map.of(
                "matchId",
                "8d86b72e-79ee-4e46-9c62-66e7974b6252",
                "playerA",
                "Roger Federer",
                "playerB",
                "Novak Djokovic",
                "startDate",
                "2021-07-03T10:00:00Z"),
            Map.of(
                "matchId",
                "c2ef67b4-c7f5-428d-82db-2732e4b46343",
                "playerA",
                "Alexander Zverev",
                "playerB",
                "Rafael Nadal",
                "startDate",
                "2021-07-04T12:00:00Z"));
  }
}

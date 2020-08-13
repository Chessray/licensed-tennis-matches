package co.uk.kleindelao.demo.tennis;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import co.uk.kleindelao.demo.tennis.domain.business.CustomersDelegate;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatch;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchStartDateTime;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutablePlayer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class CustomersIntegrationTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private CustomersDelegate customersDelegate;

  @Test
  void shouldReturnMatch() throws Exception {
    // Given
    final var customerIdValue = 1;
    final var matchId = UUID.randomUUID();
    final var playerA = "Federer";
    final var playerB = "Djokovic";
    final var startDate = ZonedDateTime.of(LocalDateTime.of(2020, 7, 23, 12, 56, 42, 451421), UTC);
    given(
            customersDelegate.getMatchesforCustomerWithId(
                ImmutableCustomerId.builder().setId(customerIdValue).build()))
        .willReturn(
            Set.of(
                ImmutableMatch.builder()
                    .setId(ImmutableMatchId.builder().setId(matchId).build())
                    .setStartDate(
                        ImmutableMatchStartDateTime.builder()
                            .setInstant(Instant.from(startDate))
                            .build())
                    .setPlayerA(ImmutablePlayer.builder().setName(playerA).build())
                    .setPlayerB(ImmutablePlayer.builder().setName(playerB).build())
                    .build()));

    // When - Then
    mockMvc
        .perform(MockMvcRequestBuilders.get("/customers/1/matches"))
        .andDo(print())
        .andExpect(jsonPath("$", instanceOf(JSONArray.class)))
        .andExpect(jsonPath("$[0].matchId", equalTo(String.valueOf(matchId))))
        .andExpect(jsonPath("$[0].startDate", equalTo("2020-07-23T12:56:42.000451421Z")))
        .andExpect(jsonPath("$[0].playerA", equalTo(playerA)))
        .andExpect(jsonPath("$[0].playerB", equalTo(playerB)));
  }
}

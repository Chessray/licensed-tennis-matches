package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static co.uk.kleindelao.demo.tennis.adapter.rest.testutil.TestObjects.match;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import co.uk.kleindelao.demo.tennis.adapter.rest.dto.MatchDto;
import co.uk.kleindelao.demo.tennis.adapter.rest.mapper.MatchMapper;
import co.uk.kleindelao.demo.tennis.adapter.rest.mapper.MatchMapperImpl;
import co.uk.kleindelao.demo.tennis.domain.business.CustomersDelegate;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import net.time4j.Moment;
import net.time4j.clock.FixedClock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CustomersControllerTest {
  private static final String PLAYER_A_NAME = "Roger Federer";
  private static final String PLAYER_B_NAME = "Novak Djokovic";
  @Mock private CustomersDelegate customersDelegate;

  @Spy private final MatchMapper matchMapper = new MatchMapperImpl();

  @InjectMocks private CustomersController controller;

  private static final int CUSTOMER_ID_VALUE = 1;

  @Test
  void shouldReturnMatches() {
    // Given
    given(
            customersDelegate.getMatchesForCustomerWithId(
                ImmutableCustomerId.builder().setId(CUSTOMER_ID_VALUE).build()))
        .willReturn(Set.of(match(PLAYER_A_NAME, PLAYER_B_NAME)));

    // When
    final var actualMatches = controller.getMatchesForCustomer(CUSTOMER_ID_VALUE, Optional.empty());

    // Then
    then(actualMatches)
        .isNotNull()
        .extracting(ResponseEntity::getStatusCode)
        .isEqualTo(HttpStatus.OK);
    then(actualMatches.getBody()).isNotNull().hasSize(1);
  }

  @Test
  void shouldAddSimpleSummaryWhenParameterIsGiven() {
    // Given
    given(
            customersDelegate.getMatchesForCustomerWithId(
                ImmutableCustomerId.builder().setId(CUSTOMER_ID_VALUE).build()))
        .willReturn(Set.of(match(PLAYER_A_NAME, PLAYER_B_NAME)));

    // When
    final var actualMatches =
        controller.getMatchesForCustomer(CUSTOMER_ID_VALUE, Optional.of("AvB"));

    // Then
    then(actualMatches)
        .isNotNull()
        .extracting(ResponseEntity::getStatusCode)
        .isEqualTo(HttpStatus.OK);
    then(actualMatches.getBody())
        .isNotNull()
        .hasSize(1)
        .extracting(MatchDto::getSummary)
        .containsExactly(Optional.of(PLAYER_A_NAME + " vs " + PLAYER_B_NAME));
  }

  @Test
  void shouldAddWithTimeSummaryWhenParameterIsGiven() {
    // Given
    final Instant matchStart = Instant.now().truncatedTo(SECONDS);
    given(
            customersDelegate.getMatchesForCustomerWithId(
                ImmutableCustomerId.builder().setId(CUSTOMER_ID_VALUE).build()))
        .willReturn(Set.of(match(PLAYER_A_NAME, PLAYER_B_NAME, matchStart)));
    matchMapper.setReferenceClock(FixedClock.of(Moment.from(matchStart)));

    // When
    final var actualMatches =
        controller.getMatchesForCustomer(CUSTOMER_ID_VALUE, Optional.of("AvBTime"));

    // Then
    then(actualMatches)
        .isNotNull()
        .extracting(ResponseEntity::getStatusCode)
        .isEqualTo(HttpStatus.OK);
    then(actualMatches.getBody())
        .isNotNull()
        .hasSize(1)
        .extracting(MatchDto::getSummary)
        .containsExactly(Optional.of(PLAYER_A_NAME + " vs " + PLAYER_B_NAME + ", starts now"));
  }
}

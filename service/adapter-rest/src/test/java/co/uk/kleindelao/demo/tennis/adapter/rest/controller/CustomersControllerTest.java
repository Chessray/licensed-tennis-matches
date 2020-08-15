package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import co.uk.kleindelao.demo.tennis.adapter.rest.mapper.MatchMapper;
import co.uk.kleindelao.demo.tennis.adapter.rest.mapper.MatchMapperImpl;
import co.uk.kleindelao.demo.tennis.domain.business.CustomersDelegate;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatch;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchStartDateTime;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutablePlayer;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
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
  @Mock private CustomersDelegate customersDelegate;
  @Spy private final MatchMapper matchMapper = new MatchMapperImpl();

  @InjectMocks private CustomersController controller;

  @Test
  void shouldReturnMatches() {
    // Given
    final var customerIdValue = 1;
    given(
            customersDelegate.getMatchesForCustomerWithId(
                ImmutableCustomerId.builder().setId(customerIdValue).build()))
        .willReturn(
            Set.of(
                ImmutableMatch.builder()
                    .setId(ImmutableMatchId.builder().setId(UUID.randomUUID()).build())
                    .setStartDate(
                        ImmutableMatchStartDateTime.builder().setInstant(Instant.now()).build())
                    .setPlayerA(ImmutablePlayer.builder().setName("Federer").build())
                    .setPlayerB(ImmutablePlayer.builder().setName("Djokovic").build())
                    .build()));

    // When
    final var actualMatches = controller.getMatchesForCustomer(customerIdValue);

    // Then
    then(actualMatches)
        .isNotNull()
        .extracting(ResponseEntity::getStatusCode)
        .isEqualTo(HttpStatus.OK);
    then(actualMatches.getBody()).isNotNull().hasSize(1);
  }
}

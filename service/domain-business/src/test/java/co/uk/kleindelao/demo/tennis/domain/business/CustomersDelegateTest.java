package co.uk.kleindelao.demo.tennis.domain.business;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomer;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatch;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchStartDateTime;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutablePlayer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomersDelegateTest {
  @Mock private MatchDataPort persistencePort;

  @InjectMocks private CustomersDelegate delegate;

  @Test
  void shouldForwardDataFromPort() {
    // Given
    final var customerId = ImmutableCustomerId.builder().setId(1).build();
    final var matches =
        Set.<Match>of(
            ImmutableMatch.builder()
                .setId(ImmutableMatchId.builder().setId(UUID.randomUUID()).build())
                .setStartDate(
                    ImmutableMatchStartDateTime.builder().setInstant(Instant.now()).build())
                .setPlayerA(ImmutablePlayer.builder().setName("Federer").build())
                .setPlayerB(ImmutablePlayer.builder().setName("Djokovic").build())
                .build());
    given(
            persistencePort.getMatchesForCustomer(
                ImmutableCustomer.builder().setCustomerId(customerId).build()))
        .willReturn(matches);

    // When
    final var result = delegate.getMatchesforCustomerWithId(customerId);

    // Then
    then(result).isEqualTo(matches);
  }
}

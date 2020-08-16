package co.uk.kleindelao.demo.tennis.domain.model;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.Instant;
import java.util.UUID;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

class MatchTest {
  @Test
  void shouldReturnPlayerAVsPlayerB() {
    // Given
    final var playerA = RandomString.make();
    final var playerB = RandomString.make();
    final var match =
        ImmutableMatch.builder()
            .setId(ImmutableMatchId.builder().setId(UUID.randomUUID()).build())
            .setStartDate(ImmutableMatchStartDateTime.builder().setInstant(Instant.now()).build())
            .setPlayerA(ImmutablePlayer.builder().setName(playerA).build())
            .setPlayerB(ImmutablePlayer.builder().setName(playerB).build())
            .build();

    // When
    final var pairing = match.getPairing();

    // Then
    then(pairing).isEqualTo(playerA + " vs " + playerB);
  }
}

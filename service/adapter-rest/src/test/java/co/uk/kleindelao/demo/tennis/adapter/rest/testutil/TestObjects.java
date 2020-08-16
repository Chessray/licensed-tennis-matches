package co.uk.kleindelao.demo.tennis.adapter.rest.testutil;

import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatch;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchStartDateTime;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutablePlayer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.time.Instant;
import java.util.UUID;

public class TestObjects {
  public static Match match(final String playerAName, final String playerBName) {
    return match(playerAName, playerBName, Instant.now());
  }

  public static Match match(
      final String playerAName, final String playerBName, final Instant startInstant) {
    return ImmutableMatch.builder()
        .setId(ImmutableMatchId.builder().setId(UUID.randomUUID()).build())
        .setStartDate(ImmutableMatchStartDateTime.builder().setInstant(startInstant).build())
        .setPlayerA(ImmutablePlayer.builder().setName(playerAName).build())
        .setPlayerB(ImmutablePlayer.builder().setName(playerBName).build())
        .build();
  }
}

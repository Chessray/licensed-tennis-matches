package co.uk.kleindelao.demo.tennis.adapter.rest.dto;

import static co.uk.kleindelao.demo.tennis.adapter.rest.dto.SummaryType.NONE;
import static co.uk.kleindelao.demo.tennis.adapter.rest.dto.SummaryType.SIMPLE;
import static co.uk.kleindelao.demo.tennis.adapter.rest.dto.SummaryType.WITH_TIME;
import static co.uk.kleindelao.demo.tennis.adapter.rest.testutil.TestObjects.match;
import static org.assertj.core.api.BDDAssertions.then;

import java.time.Instant;
import java.util.stream.Stream;
import net.time4j.Moment;
import net.time4j.SystemClock;
import net.time4j.clock.FixedClock;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SummaryTypeTest {
  @ParameterizedTest
  @MethodSource
  void shouldReturnMatchingSummaryType(
      final String queryParameterValue, final SummaryType expected) {
    then(SummaryType.forQueryParameterValue(queryParameterValue)).isSameAs(expected);
  }

  static Stream<Arguments> shouldReturnMatchingSummaryType() {
    return Stream.of(
        Arguments.of("AvB", SIMPLE),
        Arguments.of("AvBTime", WITH_TIME),
        Arguments.of(RandomString.make(20), SummaryType.NONE));
  }

  @Nested
  class None {
    @Test
    void shouldReturnEmptyOptional() {
      // Given
      final var match = match(RandomString.make(15), RandomString.make(25));

      // When
      final var summary = NONE.formatSummary(match, SystemClock.INSTANCE);

      // Then
      then(summary).isEmpty();
    }
  }

  @Nested
  class Simple {
    @Test
    void shouldReturnOptionalContainingOnlyPlayerNames() {
      // Given
      final var playerAName = RandomString.make(15);
      final var playerBName = RandomString.make(25);
      final var match = match(playerAName, playerBName);

      // When
      final var summary = SIMPLE.formatSummary(match, SystemClock.INSTANCE);

      // Then
      then(summary).isPresent().contains(playerAName + " vs " + playerBName);
    }
  }

  @Nested
  class WithTime {
    @Test
    void shouldReturnOptionalContainingPlayerNamesAndStartsInWhenMatchInFuture() {
      // Given
      final var playerAName = RandomString.make(15);
      final var playerBName = RandomString.make(25);
      final var referencePoint = Instant.now();
      final var matchStart = referencePoint.plusSeconds(600);
      final var match = match(playerAName, playerBName, matchStart);

      // When
      final var summary = WITH_TIME.formatSummary(match, FixedClock.of(Moment.from(referencePoint)));

      // Then
      then(summary).isPresent().contains(playerAName + " vs " + playerBName + ", starts in 10 minutes");
    }

    @Test
    void shouldReturnOptionalContainingPlayerNamesAndStartsNowWhenMatchIsNow() {
      // Given
      final var playerAName = RandomString.make(15);
      final var playerBName = RandomString.make(25);
      final var referencePoint = Instant.now();
      final var matchStart = referencePoint;
      final var match = match(playerAName, playerBName, matchStart);

      // When
      final var summary = WITH_TIME.formatSummary(match, FixedClock.of(Moment.from(referencePoint)));

      // Then
      then(summary).isPresent().contains(playerAName + " vs " + playerBName + ", starts now");
    }

    @Test
    void shouldReturnOptionalContainingPlayerNamesAndStartedAgoWhenMatchInPast() {
      // Given
      final var playerAName = RandomString.make(15);
      final var playerBName = RandomString.make(25);
      final var referencePoint = Instant.now();
      final var matchStart = referencePoint.minusSeconds(600);
      final var match = match(playerAName, playerBName, matchStart);

      // When
      final var summary = WITH_TIME.formatSummary(match, FixedClock.of(Moment.from(referencePoint)));

      // Then
      then(summary).isPresent().contains(playerAName + " vs " + playerBName + ", started 10 minutes ago");
    }
  }
}

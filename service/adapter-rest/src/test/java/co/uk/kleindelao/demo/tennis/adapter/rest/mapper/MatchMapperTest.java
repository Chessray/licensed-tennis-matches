package co.uk.kleindelao.demo.tennis.adapter.rest.mapper;

import static co.uk.kleindelao.demo.tennis.adapter.rest.testutil.TestObjects.match;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

import co.uk.kleindelao.demo.tennis.adapter.rest.dto.MatchDto;
import java.time.Instant;
import java.util.Optional;
import net.time4j.Moment;
import net.time4j.clock.FixedClock;
import org.junit.jupiter.api.Test;

class MatchMapperTest {
  private final MatchMapper matchMapper = new MatchMapperImpl();
  private static final String PLAYER_A_NAME = "Serena Williams";
  private static final String PLAYER_B_NAME = "Angelique Kerber";

  @Test
  void shouldReturnMatchWithoutSummaryForEmptySummaryType() {
    // When
    final var dto = matchMapper.toDto(match(PLAYER_A_NAME, PLAYER_B_NAME), Optional.empty());

    // Then
    then(dto).isNotNull().extracting(MatchDto::getSummary, as(OPTIONAL)).isEmpty();
  }

  @Test
  void shouldReturnMatchWithSimpleSummaryForAvBSummaryType() {
    // When
    final var dto = matchMapper.toDto(match(PLAYER_A_NAME, PLAYER_B_NAME), Optional.of("AvB"));

    // Then
    then(dto)
        .isNotNull()
        .extracting(MatchDto::getSummary, as(OPTIONAL))
        .isPresent()
        .contains(PLAYER_A_NAME + " vs " + PLAYER_B_NAME);
  }

  @Test
  void shouldReturnMatchWithTimeSummaryForAvBTimeSummaryType() {
    // Given
    final Instant referencePoint = Instant.now();
    matchMapper.setReferenceClock(FixedClock.of(Moment.from(referencePoint)));

    // When
    final var dto =
        matchMapper.toDto(
            match(PLAYER_A_NAME, PLAYER_B_NAME, referencePoint.minusSeconds(300)),
            Optional.of("AvBTime"));

    // Then
    then(dto)
            .isNotNull()
            .extracting(MatchDto::getSummary, as(OPTIONAL))
            .isPresent()
            .contains(PLAYER_A_NAME + " vs " + PLAYER_B_NAME + ", started 5 minutes ago");
  }
}

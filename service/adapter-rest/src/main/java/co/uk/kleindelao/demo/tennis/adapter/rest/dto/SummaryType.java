package co.uk.kleindelao.demo.tennis.adapter.rest.dto;

import static java.util.function.Function.identity;

import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.time4j.Moment;
import net.time4j.PrettyTime;
import net.time4j.base.TimeSource;

public enum SummaryType {
  NONE("") {
    @Override
    public Optional<String> formatSummary(
        final Match match, final TimeSource<Moment> referenceClock) {
      return Optional.empty();
    }
  },
  SIMPLE("AvB") {
    @Override
    public Optional<String> formatSummary(
        final Match match, final TimeSource<Moment> referenceClock) {
      return Optional.of(match.getPairing());
    }
  },
  WITH_TIME("AvBTime") {
    @Override
    public Optional<String> formatSummary(
        final Match match, final TimeSource<Moment> referenceClock) {
      final var matchStart = match.getStartDate().getInstant();
      final var startPrefix =
          matchStart.compareTo(referenceClock.currentInstant()) < 0 ? "started " : "starts ";
      final var relativeString =
          startPrefix
              + PrettyTime.of(Locale.UK)
                  .withReferenceClock(referenceClock)
                  .printRelative(matchStart, ZoneId.of("UTC"));
      return Optional.of(match.getPairing() + ", " + relativeString);
    }
  };

  private final String queryParameterValue;

  private static final Map<String, SummaryType> QUERY_PARAMETER_VALUE_2_SUMMARY_TYPE =
      Arrays.stream(values())
          .collect(Collectors.toMap(SummaryType::getQueryParameterValue, identity()));

  SummaryType(final String queryParameterValue) {
    this.queryParameterValue = queryParameterValue;
  }

  public static SummaryType forQueryParameterValue(final String queryParameterValue) {
    return QUERY_PARAMETER_VALUE_2_SUMMARY_TYPE.getOrDefault(queryParameterValue, NONE);
  }

  final String getQueryParameterValue() {
    return queryParameterValue;
  }

  public abstract Optional<String> formatSummary(
      final Match match, final TimeSource<Moment> referenceClock);
}

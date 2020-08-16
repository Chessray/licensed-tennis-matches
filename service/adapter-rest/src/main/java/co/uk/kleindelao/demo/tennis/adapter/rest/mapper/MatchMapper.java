package co.uk.kleindelao.demo.tennis.adapter.rest.mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

import co.uk.kleindelao.demo.tennis.adapter.rest.dto.MatchDto;
import co.uk.kleindelao.demo.tennis.adapter.rest.dto.SummaryType;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import net.time4j.Moment;
import net.time4j.base.TimeSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public abstract class MatchMapper {
  @Autowired
  private TimeSource<Moment> referenceClock;

  /**
   * Exposed for testing purposes.
   */
  public void setReferenceClock(TimeSource<Moment> referenceClock) {
    this.referenceClock = referenceClock;
  }

  @Mapping(target = "matchId", source = "match.id.id")
  @Mapping(target = "startDate", source = "match.startDate.instant")
  @Mapping(target = "playerA", source = "match.playerA.name")
  @Mapping(target = "playerB", source = "match.playerB.name")
  @Mapping(target = "summary", expression = "java(toSummary(match, summaryType))")
  public abstract MatchDto toDto(Match match, Optional<String> summaryType);

  protected final ZonedDateTime toZonedDateTime(final Instant instant) {
    return ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"));
  }

  protected final Optional<String> toSummary(final Match match, final Optional<String> summaryType) {
    return SummaryType.forQueryParameterValue(summaryType.orElse(""))
        .formatSummary(match, referenceClock);
  }
}

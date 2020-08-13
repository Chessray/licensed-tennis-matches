package co.uk.kleindelao.demo.tennis.adapter.rest.mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

import co.uk.kleindelao.demo.tennis.adapter.rest.dto.MatchDto;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface MatchMapper {
    @Mapping(target = "matchId", source = "id.id")
    @Mapping(target = "startDate", source = "startDate.instant")
    @Mapping(target = "playerA", source = "playerA.name")
    @Mapping(target = "playerB", source = "playerB.name")
    @Mapping(target = "summary", ignore = true)
    MatchDto toDto(Match match);

    default ZonedDateTime toZonedDateTime(final Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"));
    }
}

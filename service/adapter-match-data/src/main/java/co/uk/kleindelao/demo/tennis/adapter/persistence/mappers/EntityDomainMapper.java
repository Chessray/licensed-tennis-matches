package co.uk.kleindelao.demo.tennis.adapter.persistence.mappers;

import static org.mapstruct.ReportingPolicy.ERROR;

import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.CustomerEntity;
import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.MatchEntity;
import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.PlayerEntity;
import co.uk.kleindelao.demo.tennis.domain.model.Customer;
import co.uk.kleindelao.demo.tennis.domain.model.CustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchStartDateTime;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import co.uk.kleindelao.demo.tennis.domain.model.MatchId;
import co.uk.kleindelao.demo.tennis.domain.model.MatchStartDateTime;
import co.uk.kleindelao.demo.tennis.domain.model.Player;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface EntityDomainMapper {
  Player toDomainPlayer(PlayerEntity entity);

  @Mapping(target = "customerId", source = "id")
  Customer toDomainCustomer(CustomerEntity entity);

  default CustomerId wrap(final int rawId) {
    return ImmutableCustomerId.builder().setId(rawId).build();
  }

  @Mapping(target = "playerA", expression = "java(playerIdMap.get(entity.getPlayerA()))")
  @Mapping(target = "playerB", expression = "java(playerIdMap.get(entity.getPlayerB()))")
  Match toDomainMatch(MatchEntity entity, Map<UUID, Player> playerIdMap);

  default MatchId wrap(final UUID rawId) {
    return ImmutableMatchId.builder().setId(rawId).build();
  }

  default MatchStartDateTime wrap(final ZonedDateTime rawDate) {
    return ImmutableMatchStartDateTime.builder().setInstant(rawDate.toInstant()).build();
  }
}

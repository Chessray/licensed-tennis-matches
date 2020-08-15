package co.uk.kleindelao.demo.tennis.adapter.persistence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
@JsonDeserialize(as = ImmutableTournamentData.class)
public abstract class TournamentData {
  public abstract Set<CustomerEntity> getCustomers();

  public abstract Set<PlayerEntity> getPlayers();

  public abstract Set<Tournament> getTournaments();
}

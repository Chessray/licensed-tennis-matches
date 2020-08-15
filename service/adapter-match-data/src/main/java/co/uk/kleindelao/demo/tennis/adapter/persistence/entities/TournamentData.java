package co.uk.kleindelao.demo.tennis.adapter.persistence.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*", jdkOnly = true)
@JsonDeserialize(as = ImmutableTournamentData.class)
public abstract class TournamentData {
  public abstract Set<CustomerEntity> getCustomers();

  public abstract Set<PlayerEntity> getPlayers();

  public abstract Set<Tournament> getTournaments();
}

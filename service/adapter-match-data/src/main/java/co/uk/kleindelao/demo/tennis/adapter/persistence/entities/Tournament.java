package co.uk.kleindelao.demo.tennis.adapter.persistence.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*", jdkOnly = true)
@JsonDeserialize(as = ImmutableTournament.class)
public abstract class Tournament {
  public abstract String getName();

  public abstract Set<Integer> getLicensees();

  public abstract Set<MatchEntity> getMatches();
}

package co.uk.kleindelao.demo.tennis.adapter.persistence.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*", jdkOnly = true)
@JsonDeserialize(as = ImmutableMatchEntity.class)
public abstract class MatchEntity {
  public abstract UUID getId();

  public abstract ZonedDateTime getStartDate();

  public abstract UUID getPlayerA();

  public abstract UUID getPlayerB();

  public abstract Set<Integer> getLicensees();
}

package co.uk.kleindelao.demo.tennis.adapter.rest.dto;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class MatchDto {
  public abstract UUID getMatchId();

  public abstract ZonedDateTime getStartDate();

  public abstract String getPlayerA();

  public abstract String getPlayerB();

  public abstract Optional<String> getSummary();
}

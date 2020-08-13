package co.uk.kleindelao.demo.tennis.domain.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class Match {
  public abstract MatchId getId();

  public abstract MatchStartDateTime getStartDate();

  public abstract Player getPlayerA();

  public abstract Player getPlayerB();
}

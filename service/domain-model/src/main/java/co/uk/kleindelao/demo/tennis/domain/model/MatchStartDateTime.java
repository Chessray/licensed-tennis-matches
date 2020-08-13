package co.uk.kleindelao.demo.tennis.domain.model;

import java.time.Instant;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class MatchStartDateTime {
    public abstract Instant getInstant();
}

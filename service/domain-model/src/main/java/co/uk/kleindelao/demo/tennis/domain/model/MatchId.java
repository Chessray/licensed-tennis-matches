package co.uk.kleindelao.demo.tennis.domain.model;

import java.util.UUID;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class MatchId {
    public abstract UUID getId();
}

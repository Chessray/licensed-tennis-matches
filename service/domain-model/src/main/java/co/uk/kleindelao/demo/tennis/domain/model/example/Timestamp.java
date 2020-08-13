package co.uk.kleindelao.demo.tennis.domain.model.example;

import java.time.ZonedDateTime;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class Timestamp {
    public abstract ZonedDateTime getDateTime();
}

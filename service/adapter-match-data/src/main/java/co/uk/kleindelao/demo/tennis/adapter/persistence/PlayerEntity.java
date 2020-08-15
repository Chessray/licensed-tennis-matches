package co.uk.kleindelao.demo.tennis.adapter.persistence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.UUID;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
@JsonDeserialize(as = ImmutablePlayerEntity.class)
public abstract class PlayerEntity {
  public abstract UUID getId();

  public abstract String getName();
}

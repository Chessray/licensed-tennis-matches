package co.uk.kleindelao.demo.tennis.adapter.persistence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
@JsonDeserialize(as = ImmutableCustomerEntity.class)
public abstract class CustomerEntity {
    public abstract int getId();
}

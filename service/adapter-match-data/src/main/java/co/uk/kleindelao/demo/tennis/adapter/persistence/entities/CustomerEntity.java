package co.uk.kleindelao.demo.tennis.adapter.persistence.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*", jdkOnly = true)
@JsonDeserialize(as = ImmutableCustomerEntity.class)
public abstract class CustomerEntity {
    public abstract int getId();
}

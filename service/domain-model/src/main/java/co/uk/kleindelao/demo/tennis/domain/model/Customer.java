package co.uk.kleindelao.demo.tennis.domain.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class Customer {
    public abstract CustomerId getCustomerId();
}

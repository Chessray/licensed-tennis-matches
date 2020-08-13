package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(defaults = @Value.Immutable(copy = false), init = "set*")
public abstract class TimestampDto {
    public abstract String getFormattedTimestamp();
}

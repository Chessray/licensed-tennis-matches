package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.BDDAssertions.then;

import co.uk.kleindelao.demo.tennis.domain.model.example.ImmutableTimestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class TimestampMapperTest {
    private final TimestampMapper mapper = new TimestampMapperImpl();

    @Test
    void shouldReturnNullForNull() {
        then(mapper.toDto(null)).isNull();
    }

    @Test
    void shouldMapWithDateTime() {
        final var dto = mapper.toDto(ImmutableTimestamp.builder()
                                                       .setDateTime(ZonedDateTime.ofInstant(LocalDateTime.of(2020, 7, 24, 13,
                                                               55, 34, 290393), UTC, ZoneId.of("UTC").normalized()))
                                                       .build());

        then(dto).isNotNull();
        then(dto.getFormattedTimestamp()).isEqualTo("2020-07-24T13:55:34.000290393Z");
    }
}
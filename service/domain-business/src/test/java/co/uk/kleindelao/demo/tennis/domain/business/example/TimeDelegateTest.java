package co.uk.kleindelao.demo.tennis.domain.business.example;

import static org.assertj.core.api.BDDAssertions.then;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class TimeDelegateTest {
    private static final ZonedDateTime FIXED_DATE_TIME =
        ZonedDateTime.of(LocalDateTime.of(2020, 7, 22, 12, 16, 23, 345), ZoneOffset.UTC);

    private final TimeDelegate timeDelegate =
        new TimeDelegate(Clock.fixed(Instant.from(FIXED_DATE_TIME), FIXED_DATE_TIME.getZone()));

    @Test
    void shouldReturnDateTimeFromClock() {
        final var delegateTimestamp = timeDelegate.getDateTime();

        then(delegateTimestamp.getDateTime()).isEqualTo(FIXED_DATE_TIME);
    }
}

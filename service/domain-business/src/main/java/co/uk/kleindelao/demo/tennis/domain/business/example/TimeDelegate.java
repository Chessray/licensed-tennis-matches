package co.uk.kleindelao.demo.tennis.domain.business.example;

import co.uk.kleindelao.demo.tennis.domain.model.example.ImmutableTimestamp;
import co.uk.kleindelao.demo.tennis.domain.model.example.Timestamp;
import java.time.Clock;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Service;

@Service
public class TimeDelegate {
    private final Clock clock;

    public TimeDelegate(final Clock clock) {
        this.clock = clock;
    }

    /**
     * Returns the internal {@link Clock}'s current date and time wrapped into a {@link Timestamp}.
     *
     * @return The internal {@link Clock}'s current date and time wrapped into a {@link Timestamp}.
     */
    public Timestamp getDateTime() {
        return ImmutableTimestamp.builder()
                                 .setDateTime(ZonedDateTime.ofInstant(clock.instant(), clock.getZone()))
                                 .build();
    }
}

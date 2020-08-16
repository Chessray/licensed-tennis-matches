package co.uk.kleindelao.demo.tennis;

import java.time.Clock;
import net.time4j.Moment;
import net.time4j.SystemClock;
import net.time4j.base.TimeSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    /**
     * Returns the {@link Clock} used for time-dependent {@link org.springframework.stereotype.Component Components}.
     *
     * @return The {@link Clock} used for time-dependent {@link org.springframework.stereotype.Component Components}.
     */
    @Bean
    public Clock clockBean() {
        return Clock.systemUTC();
    }

    @Bean
    public TimeSource<Moment> timeSourceBean() {
        return SystemClock.INSTANCE;
    }
}

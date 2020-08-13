package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

import co.uk.kleindelao.demo.tennis.domain.business.example.TimeDelegate;
import co.uk.kleindelao.demo.tennis.domain.model.example.ImmutableTimestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeControllerTest {
    private static final ZonedDateTime FIXED_DATE_TIME =
        ZonedDateTime.of(LocalDateTime.of(2020, 7, 22, 14, 2, 10, 458), UTC);

    @Mock
    private TimeDelegate timeDelegate;
    @Mock
    private TimestampMapper timestampMapper;

    @InjectMocks
    private TimeController timeController;

    @Test
    void shouldReturnMappedDateTimeFromDelegate() {
        final var timestamp = ImmutableTimestamp.builder()
                                                .setDateTime(FIXED_DATE_TIME)
                                                .build();
        final var expectedBodyString = FIXED_DATE_TIME.format(
            ISO_ZONED_DATE_TIME);
        given(timeDelegate.getDateTime()).willReturn(timestamp);
        given(timestampMapper.toDto(timestamp)).willReturn(ImmutableTimestampDto.builder()
                                                                                .setFormattedTimestamp(
                                                                                    expectedBodyString)
                                                                                .build());

        final var response = timeController.getFormattedTimestamp();

        then(response).isNotNull();
        then(response.getStatusCode()).isSameAs(OK);
        final var responseBody = response.getBody();
        then(responseBody).isNotNull();
        then(responseBody.getFormattedTimestamp()).isNotNull()
                                                  .isEqualTo(expectedBodyString);
    }
}

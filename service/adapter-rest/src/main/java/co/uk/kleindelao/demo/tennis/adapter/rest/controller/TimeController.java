package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import co.uk.kleindelao.demo.tennis.domain.business.example.TimeDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time-example")
public class TimeController {
    private final TimeDelegate timeDelegate;
    private final TimestampMapper timestampMapper;

    public TimeController(final TimeDelegate timeDelegate,
                          final TimestampMapper timestampMapper) {
        this.timeDelegate = timeDelegate;
        this.timestampMapper = timestampMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TimestampDto> getFormattedTimestamp() {
        return ResponseEntity.ok(timestampMapper.toDto(timeDelegate.getDateTime()));
    }
}

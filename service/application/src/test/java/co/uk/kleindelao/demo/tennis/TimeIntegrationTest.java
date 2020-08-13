package co.uk.kleindelao.demo.tennis;

import static java.time.ZoneOffset.UTC;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import co.uk.kleindelao.demo.tennis.domain.business.example.TimeDelegate;
import co.uk.kleindelao.demo.tennis.domain.model.example.ImmutableTimestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
class TimeIntegrationTest {
    private static final ZonedDateTime FIXED_DATE_TIME =
        ZonedDateTime.of(LocalDateTime.of(2020, 7, 23, 12, 56, 42, 451421), UTC);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeDelegate timeDelegate;

    @Test
    void shouldReturnTime() throws Exception {
        given(timeDelegate.getDateTime())
            .willReturn(ImmutableTimestamp.builder()
                                          .setDateTime(FIXED_DATE_TIME)
                                          .build());

        mockMvc.perform(MockMvcRequestBuilders.get("/time-example"))
               .andDo(print())
               .andExpect(jsonPath("$.formattedTimestamp", equalTo("2020-07-23T12:56:42.000451421Z")));
    }
}

package co.uk.kleindelao.demo.tennis.adapter.persistence;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.ITERABLE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

class MatchDataAdapterTest {
  private MatchDataAdapter adapter;

  @BeforeEach
  void initialiseAdapter() throws IOException {
    adapter =
        new MatchDataAdapter(
            new DefaultResourceLoader(), new ObjectMapper().registerModule(new JavaTimeModule()));
  }

  @Test
  void shouldReadTournamentData() {
    // When
    final var tournamentData = adapter.getTournamentData();

    // Then
    then(tournamentData).isNotNull();
    then(tournamentData).extracting(TournamentData::getCustomers, as(ITERABLE)).hasSize(4);
    then(tournamentData).extracting(TournamentData::getPlayers, as(ITERABLE)).hasSize(4);
    then(tournamentData).extracting(TournamentData::getTournaments, as(ITERABLE)).hasSize(3);
  }
}

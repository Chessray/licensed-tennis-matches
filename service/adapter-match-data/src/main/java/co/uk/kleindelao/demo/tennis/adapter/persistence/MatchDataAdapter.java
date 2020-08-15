package co.uk.kleindelao.demo.tennis.adapter.persistence;

import co.uk.kleindelao.demo.tennis.domain.business.MatchDataPort;
import co.uk.kleindelao.demo.tennis.domain.model.Customer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * For demo purposes, this reads from static input data. In a real-world scenario, this would
 * connect to a database or another service.
 */
@Service
public class MatchDataAdapter implements MatchDataPort {
  private final TournamentData tournamentData;

  public MatchDataAdapter(final ResourceLoader resourceLoader, final ObjectMapper objectMapper)
      throws IOException {
    tournamentData =
        objectMapper.readValue(
            resourceLoader.getResource("/tournamentData.json").getInputStream(),
            TournamentData.class);
  }

  TournamentData getTournamentData() {
    return tournamentData;
  }

  @Override
  public Set<Match> getMatchesForCustomer(final Customer customer) {
    throw new UnsupportedOperationException("Not yet implemented!");
  }
}

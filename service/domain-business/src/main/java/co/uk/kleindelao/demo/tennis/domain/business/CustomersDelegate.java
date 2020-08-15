package co.uk.kleindelao.demo.tennis.domain.business;

import co.uk.kleindelao.demo.tennis.domain.model.CustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CustomersDelegate {
  private final MatchDataPort persistencePort;

  public CustomersDelegate(final MatchDataPort persistencePort) {
    this.persistencePort = persistencePort;
  }

  public Set<Match> getMatchesForCustomerWithId(final CustomerId customerId) {
    return persistencePort.getMatchesForCustomer(
        ImmutableCustomer.builder().setCustomerId(customerId).build());
  }
}

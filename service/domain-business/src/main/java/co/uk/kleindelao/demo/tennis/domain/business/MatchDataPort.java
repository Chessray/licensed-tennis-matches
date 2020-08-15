package co.uk.kleindelao.demo.tennis.domain.business;

import co.uk.kleindelao.demo.tennis.domain.model.Customer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.util.Set;

public interface MatchDataPort {
    Set<Match> getMatchesForCustomer(Customer customer);
}

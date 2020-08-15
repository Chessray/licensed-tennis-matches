package co.uk.kleindelao.demo.tennis.adapter.persistence;

import co.uk.kleindelao.demo.tennis.domain.business.MatchDataPort;
import co.uk.kleindelao.demo.tennis.domain.model.Customer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class MatchDataAdapter implements MatchDataPort {
    @Override
    public Set<Match> getMatchesForCustomer(final Customer customer) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}

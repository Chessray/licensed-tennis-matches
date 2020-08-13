package co.uk.kleindelao.demo.tennis.domain.business;

import co.uk.kleindelao.demo.tennis.domain.model.CustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CustomersDelegate {
    public Set<Match> getMatchesforCustomerWithId(final CustomerId customerId) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}

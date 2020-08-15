package co.uk.kleindelao.demo.tennis.adapter.persistence;

import static java.util.stream.Collectors.toMap;

import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.CustomerEntity;
import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.MatchEntity;
import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.PlayerEntity;
import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.Tournament;
import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.TournamentData;
import co.uk.kleindelao.demo.tennis.adapter.persistence.mappers.EntityDomainMapper;
import co.uk.kleindelao.demo.tennis.domain.business.MatchDataPort;
import co.uk.kleindelao.demo.tennis.domain.model.Customer;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import co.uk.kleindelao.demo.tennis.domain.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * For demo purposes, this reads from static input data. In a real-world scenario, this would
 * connect to a database or another service.
 */
@Service
public class MatchDataAdapter implements MatchDataPort {
  private final TournamentData tournamentData;
  private final SetMultimap<Customer, Match> licensedMatches;

  public MatchDataAdapter(
      final ResourceLoader resourceLoader,
      final ObjectMapper objectMapper,
      final EntityDomainMapper domainMapper)
      throws IOException {
    tournamentData =
        objectMapper.readValue(
            resourceLoader.getResource("classpath:/tournamentData.json").getInputStream(),
            TournamentData.class);
    licensedMatches = HashMultimap.create();
    populateLicensedMatches(domainMapper);
  }

  private void populateLicensedMatches(final EntityDomainMapper domainMapper) {
    final var playerIdMap =
        tournamentData.getPlayers().stream()
            .collect(toMap(PlayerEntity::getId, domainMapper::toDomainPlayer));
    final var customerMap =
        tournamentData.getCustomers().stream()
            .collect(toMap(CustomerEntity::getId, domainMapper::toDomainCustomer));
    tournamentData
        .getTournaments()
        .forEach(
            tournament ->
                populateLicensedMatches(tournament, playerIdMap, customerMap, domainMapper));
  }

  private void populateLicensedMatches(
      final Tournament tournament,
      final Map<UUID, Player> playerMap,
      final Map<Integer, Customer> customerMap,
      final EntityDomainMapper domainMapper) {
    tournament
        .getMatches()
        .forEach(
            matchEntity ->
                registerForLicensees(
                    matchEntity, tournament, playerMap, customerMap, domainMapper));
  }

  private void registerForLicensees(
      final MatchEntity matchEntity,
      final Tournament tournament,
      final Map<UUID, Player> playerMap,
      final Map<Integer, Customer> customerMap,
      final EntityDomainMapper domainMapper) {
    final var domainMatch = domainMapper.toDomainMatch(matchEntity, playerMap);
    registerForLicensees(tournament.getLicensees(), customerMap, domainMatch);
    registerForLicensees(matchEntity.getLicensees(), customerMap, domainMatch);
  }

  private void registerForLicensees(
      final Set<Integer> licensees,
      final Map<Integer, Customer> customerMap,
      final Match domainMatch) {
    licensees.forEach(licenseeId -> licensedMatches.put(customerMap.get(licenseeId), domainMatch));
  }

  TournamentData getTournamentData() {
    return tournamentData;
  }

  @Override
  public Set<Match> getMatchesForCustomer(final Customer customer) {
    return licensedMatches.get(customer);
  }
}

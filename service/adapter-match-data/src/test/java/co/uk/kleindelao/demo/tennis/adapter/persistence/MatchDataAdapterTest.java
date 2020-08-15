package co.uk.kleindelao.demo.tennis.adapter.persistence;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.ITERABLE;

import co.uk.kleindelao.demo.tennis.adapter.persistence.entities.TournamentData;
import co.uk.kleindelao.demo.tennis.adapter.persistence.mappers.EntityDomainMapperImpl;
import co.uk.kleindelao.demo.tennis.domain.model.Customer;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomer;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableMatchId;
import co.uk.kleindelao.demo.tennis.domain.model.Match;
import co.uk.kleindelao.demo.tennis.domain.model.MatchId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

class MatchDataAdapterTest {
  private static final int CUSTOMER_ID_SINGLE_MATCHES = 1;
  private static final int CUSTOMER_ID_SINGLE_TOURNAMENT_CUSTOMER = 3;
  private static final int CUSTOMER_ID_MIXED_CUSTOMER = 4;
  private MatchDataAdapter adapter;

  @BeforeEach
  void initialiseAdapter() throws IOException {
    adapter =
        new MatchDataAdapter(
            new DefaultResourceLoader(),
            new ObjectMapper().registerModule(new JavaTimeModule()),
            new EntityDomainMapperImpl());
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

  @Test
  void shouldReturnMatchesForSingleMatchLicenseCustomer() {
    // When
    final var matches = adapter.getMatchesForCustomer(customer(CUSTOMER_ID_SINGLE_MATCHES));

    // Then
    then(matches)
        .hasSize(2)
        .extracting(Match::getId)
        .containsExactlyInAnyOrder(
            matchId("7fde323c-a5d9-4034-b2c8-93fbaad728bd"),
            matchId("8d86b72e-79ee-4e46-9c62-66e7974b6252"));
  }

  @Test
  void shouldReturnMatchesForSingleTournamentLicenseCustomer() {
    // When
    final var matches =
        adapter.getMatchesForCustomer(customer(CUSTOMER_ID_SINGLE_TOURNAMENT_CUSTOMER));

    // Then
    then(matches)
        .hasSize(2)
        .extracting(Match::getId)
        .containsExactlyInAnyOrder(
            matchId("8d86b72e-79ee-4e46-9c62-66e7974b6252"),
            matchId("c2ef67b4-c7f5-428d-82db-2732e4b46343"));
  }

  @Test
  void shouldReturnMatchesForMIXEDLicenseCustomer() {
    // When
    final var matches = adapter.getMatchesForCustomer(customer(CUSTOMER_ID_MIXED_CUSTOMER));

    // Then
    then(matches)
        .hasSize(3)
        .extracting(Match::getId)
        .containsExactlyInAnyOrder(
            matchId("0dde751c-ae3a-4438-b83d-69114341afb9"),
            matchId("39725260-922c-45f3-a1a6-8b2926d9c8d9"),
            matchId("7fde323c-a5d9-4034-b2c8-93fbaad728bd"));
  }

  private static MatchId matchId(final String idValue) {
    return ImmutableMatchId.builder().setId(UUID.fromString(idValue)).build();
  }

  private static Customer customer(final int customerId) {
    return ImmutableCustomer.builder()
        .setCustomerId(ImmutableCustomerId.builder().setId(customerId).build())
        .build();
  }
}

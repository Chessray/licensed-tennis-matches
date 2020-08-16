package co.uk.kleindelao.demo.tennis.adapter.rest.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import co.uk.kleindelao.demo.tennis.adapter.rest.dto.MatchDto;
import co.uk.kleindelao.demo.tennis.adapter.rest.mapper.MatchMapper;
import co.uk.kleindelao.demo.tennis.domain.business.CustomersDelegate;
import co.uk.kleindelao.demo.tennis.domain.model.ImmutableCustomerId;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomersController {
  private final CustomersDelegate customersDelegate;
  private final MatchMapper matchMapper;

  public CustomersController(
      final CustomersDelegate customersDelegate, final MatchMapper matchMapper) {
    this.customersDelegate = customersDelegate;
    this.matchMapper = matchMapper;
  }

  @GetMapping(path = "/{customerId}/matches", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MatchDto>> getMatchesForCustomer(
      final @PathVariable int customerId, final @RequestParam Optional<String> summaryType) {
    return ResponseEntity.ok(
        customersDelegate
            .getMatchesForCustomerWithId(ImmutableCustomerId.builder().setId(customerId).build())
            .stream()
            .map(match -> matchMapper.toDto(match, summaryType))
            .collect(toList()));
  }
}

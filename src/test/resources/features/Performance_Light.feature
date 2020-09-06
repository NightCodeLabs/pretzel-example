Feature: Performance Test

  @Performance
  Scenario: Request a forced yes answer
    When 2 users request a forced yes at 2 users/second for 1 min
    Then the answer is returned within 10000 milliseconds
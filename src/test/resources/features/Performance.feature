Feature: Performance Test

  @Performance
  Scenario Outline:  Request a forced answer
    Given Multiple users are requesting for a forced answer
      | Max Users Load | Users Load Per Second | Test Time | Max RPS |Task  | Weight  |
      |<Max Users Load>|<Users Load Per Second>|<Test Time>|<Max RPS>|<Task>| <Weight>|
    Then The answer is returned within the expected time
      | Expected Time |
      |<Expected Time>|

    Examples:
      |Max Users Load|Users Load Per Second | Test Time | Max RPS |Task         | Expected Time | Weight|
      |1             |                   1  |          1|        1|ForcedYes	|          10000|1      |
      |2             |                   1  |          1|        2|ForcedNo	    |          10000|2      |

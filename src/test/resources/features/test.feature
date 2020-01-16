
Feature: Locust Performance Test YESNO Api

  Scenario Outline:  Request a forced Yes answer
    Given Multiple users are requesting for a forced YES answer
      | Max Users Load | Users Load Per Second | Test Time | Max RPS |Task  |
      |<Max Users Load>|<Users Load Per Second>|<Test Time>|<Max RPS>|<Task>|
    Then The answer is returned within the expected time
      | Expected Time |
      |<Expected Time>|

    Examples:
      |Max Users Load|Users Load Per Second | Test Time | Max RPS |Task            | Expected Time |
      |1             |                   1  |          1|        1|YesNoApi		 		 |           5000|

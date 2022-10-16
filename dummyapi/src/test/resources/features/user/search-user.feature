Feature: Search user.
  As a manager user
  I need a way to search any kind of user
  for perform the admin role functions.

  Scenario: Search all users.
    Given the admin user are in the searching section
    When he search for all users in general
    Then he should see the list of all users in general.

  Scenario: Search an user by id.
    Given the admin user are in the searching section with an user in mind
    When he search the specific user by the identification code
    Then he should see the specific user information.
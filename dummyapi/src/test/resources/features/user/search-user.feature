Feature: Search user.
  As a manager user
  I need a way to search any kind of user
  for perform the admin role functions.

  Background:
    Given the admin user are in the searching section

  Scenario: Search all users.
    When he search for all users in general
    Then he should see the list of all users in general.

  Scenario: Search an user by id.
    When he search a specific user by the identification code
    Then he should see the specific user information.
Feature: Create user.
  As a manager user
  I need a way to create any kind of user
  for perform the admin role functions and so, the user would star they work process too.

  Background:
    Given the admin user are in the creation user section

  Scenario: Create a normal user.
    When he configure and init the user creation
    Then he should see in the system a user according to previous user creation.

  Scenario: Try to create a user without data.
    When he configure and init the user creation without data
    Then he should see a system error message that describe a body no valid format for all fields
Feature: Create user.
  As a manager user
  I need a way to create any kind of user
  for perform the admin role functions and so, the user would star they work process too.

  Scenario: Create a normal user.
    Given the admin user are in the creation user section
    When he configure and init the user creation
    Then he should see in the system a user according to previous user creation.


Feature: Update user.
  As a manager user
  I need a way to update any kind of user information
  for perform the admin role functions and so, the user would continue they work process too.

  Scenario: Update a normal user.
    Given the admin user are in the update user information section
    When he configure the new information and init the user updating
    Then he should see the user information according to previous user modification.

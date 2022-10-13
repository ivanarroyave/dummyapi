Feature: Delete user.
  As a manager user
  I need a way to delete any kind of user
  for perform the admin role functions.

  Scenario: Delete an user.
    Given the admin user are in the remove users section
    When he select an user to delete
    Then he should see that the selected user is really removed from the system.

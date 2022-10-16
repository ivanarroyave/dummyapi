Feature: Delete user.
  As a manager user
  I need a way to delete any kind of user
  for perform the admin role functions.

  Background:
    Given the admin user are in the remove users section

  Scenario: Delete an user.
    When he select an user to delete
    Then he should see that the selected user is really removed from the system.

  Scenario: Delete an user - App-id not exists in header configuration.
    When the manager user proceed to delete the user
    Then he should see an error type named "APP_ID_NOT_EXIST"

  Scenario: Delete an user - App-id missing in header configuration.
    When the manager user proceed to delete an user
    Then he should see an error type named like "APP_ID_MISSING"

  Scenario: Delete a user with an invalid identification.
    When the manager user proceed to delete an user using an invalid identification
    Then he should see an error reference to "PARAMS_NOT_VALID"

  Scenario: Delete a user with an identification deleted previously.
    When the manager user proceed to delete an user that previously was deleted
    Then he should see an error type reference to "RESOURCE_NOT_FOUND"

  Scenario: Try to delete some user without user identification.
    When the manager user proceed to delete a user but without user identification
    Then he should see a message related to error type "PATH_NOT_FOUND"

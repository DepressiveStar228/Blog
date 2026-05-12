@Registration
Feature: Registration
  As an Unregistered User of the application
  I want to register a new account
  In order to access the blog functionality

  Background: User navigates to Registration page
    Given I navigate to the "Registration" page

  @SuccessfulRegistration
  Scenario Outline: Successful Registration using valid credentials
    When I fill in "username" with "<username>"
    And I fill in "email" with "<email>"
    And I fill in "password" with "<password>"
    And I click on the "Register" button
    Then I should be successfully registered
    And I should land on the "Login" page
    And I should see "success" message as "Реєстрація успішна! Увійдіть в акаунт."
    Examples:
      | username | email                    | password  |
      | Artem    | artem@example.com        | pass123   |

  @DisabledRegistration
  Scenario Outline: Disabled Registration when one of the required fields is left blank
    When I fill in "username" with "<username>"
    And I fill in "email" with "<email>"
    And I fill in "password" with "<password>"
    And I click on the "Register" button
    Then I should see "<form error>" message for "<input field>" field on "Registration" page
    And I should not be able to submit the "Registration" form
    Examples:
      | username | email             | password | form error                                      | input field |
      |          | artem@example.com | pass123  | The username is required and cannot be empty    | username    |
      | Artem    |                   | pass123  | The email is required and cannot be empty       | email       |
      | Artem    | notanemail        | pass123  | The email address is not valid                  | email       |
      | Artem    | artem@example.com |          | The password is required and cannot be empty    | password    |
      | Artem    | artem@example.com | abc      | Пароль мінімум 6 символів                       | password    |

  @DuplicateRegistration
  Scenario Outline: Failed Registration when email or username is already taken
    When I fill in "username" with "<username>"
    And I fill in "email" with "<email>"
    And I fill in "password" with "<password>"
    And I click on the "Register" button
    Then I should see "<error>" message on "Registration" page
    Examples:
      | username | email             | password | error                  |
      | Artem    | artem@example.com | pass123  | Email вже зайнятий     |
      | Artem    | other@example.com | pass123  | Username вже зайнятий  |

@ForgotPassword
Feature: Forgot Password
  As a Registered User of the application
  I want to reset my password via Forgot Password functionality
  In order to regain access to my account

  Background: A Registered User navigates to Login page
    Given I am a registered user
    And I navigate to the "Login" page
    And I click on "Забув пароль" link
    And I navigate to the "Forgot Password" page

  @SuccessfulForgotPassword
  Scenario Outline: Successful submit of Forgot Password form with registered email
    When I fill in "email" with "<email>"
    And I click on the "Відправити посилання" button
    Then I should land on the "Forgot Password" page
    And I should see "success" message as "Якщо такий email зареєстрований, лист відправлено."
    Examples:
      | email             |
      | artem@example.com |

  @UnregisteredEmailForgotPassword
  Scenario Outline: Submit of Forgot Password form with unregistered email
    When I fill in "email" with "<email>"
    And I click on the "Відправити посилання" button
    Then I should land on the "Forgot Password" page
    And I should see "success" message as "Якщо такий email зареєстрований, лист відправлено."
    And no reset email should be sent
    Examples:
      | email                    |
      | unknown@example.com      |

  @DisabledForgotPassword
  Scenario: Disabled Forgot Password form when email field is blank
    When I leave "email" field blank
    And I click on the "Відправити посилання" button
    Then I should not be able to submit the "Forgot Password" form

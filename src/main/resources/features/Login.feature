@Login
Feature: Login
  As a Registered User of the application
  I want to login to my account
  In order to access the blog functionality

  Background: A Registered User navigates to Login page
    Given I am a registered user
    And I navigate to the "Login" page

  @SuccessfulLogin
  Scenario Outline: Successful Login using valid credentials
    When I fill in "email" with "<email>"
    And I fill in "password" with "<password>"
    And I click on the "Увійти" button
    Then I should be successfully logged in
    And I should land on the "Home" page
    And I should see "Мій кабінет" and "Вийти" links
    Examples:
      | email             | password |
      | artem@example.com | pass123  |

  @FailedLogin
  Scenario Outline: Failed Login using wrong credentials
    When I fill in "email" with "<email>"
    And I fill in "password" with "<password>"
    And I click on the "Увійти" button
    Then I should be redirected to the "Login" page
    And I should see "error" message as "Неправильний email або пароль"
    Examples:
      | email              | password    |
      | wrong@example.com  | pass123     |
      | artem@example.com  | wrongpass   |
      | artem@example.com  | PASS123     |

  @DisabledLogin
  Scenario Outline: Disabled Login when one of the required fields is left blank
    When I fill in "email" with "<email>"
    And I fill in "password" with "<password>"
    And I click on the "Увійти" button
    Then I should see "<form error>" message for "<input field>" field on "Login" page
    And I should not be able to submit the "Login" form
    Examples:
      | email             | password | form error                                   | input field |
      |                   | pass123  | The email is required and cannot be empty    | email       |
      | artem@example.com |          | The password is required and cannot be empty | password    |

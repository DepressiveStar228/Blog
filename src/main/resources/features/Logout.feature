@Logout
Feature: Logout
  As a Logged In User of the application
  I want to Logout successfully
  In order to end my session securely

  Background: User is logged in and is on Homepage
    Given I am a logged in user
    And I navigate to the "Home" page

  @SuccessfulLogout
  Scenario: Successful Logout
    When I click on "Вийти" button on the "Home" page
    And I confirm the logout dialog
    Then I should be successfully logged out
    And I should land on the "Home" page
    And I should see "Увійти" and "Реєстрація" links
    And I should not see "Мій кабінет" and "Вийти" links

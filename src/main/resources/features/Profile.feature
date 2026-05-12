@Profile
Feature: View/Edit Profile
  As a Registered User of the application
  I want to view and update my profile
  In order to manage my personal information

  Background: User is logged in and is on Homepage
    Given I am a logged in user
    And I navigate to the "Home" page

  @ViewOwnProfile
  Scenario: Successful View of own Profile
    When I click on "Мій кабінет" link on the "Home" page
    Then I should land on the "Profile" page
    And I should see my username on the Profile page
    And I should see my bio on the Profile page
    And I should see my registration date on the Profile page
    And I should see "Редагувати профіль" link on the Profile page
    And I should see list of my posts on the Profile page

  @ViewPublicProfile
  Scenario: Successful View of another user's public Profile
    When I click on a username link on the "Home" page
    Then I should land on the "Profile" page of that user
    And I should see the user's username on the Profile page
    And I should see the user's bio on the Profile page
    And I should see list of that user's posts on the Profile page
    And I should not see "Редагувати профіль" link on the Profile page

  @SuccessfulEditProfile
  Scenario Outline: Successful Edit of own Profile
    When I click on "Мій кабінет" link on the "Home" page
    And I click on "Редагувати профіль" link
    And I fill in "username" with "<username>"
    And I fill in "bio" with "<bio>"
    And I click on the "Зберегти" button
    Then I should land on the "Profile" page
    And I should see "success" message as "Профіль оновлено!"
    And I should see updated username on the Profile page
    And I should see updated bio on the Profile page
    Examples:
      | username | bio                  |
      | Artem    | Software developer   |

  @DuplicateUsernameEdit
  Scenario: Failed Edit Profile when username is already taken
    When I click on "Мій кабінет" link on the "Home" page
    And I click on "Редагувати профіль" link
    And I fill in "username" with an already existing username
    And I click on the "Зберегти" button
    Then I should see "error" message as "Username вже зайнятий"

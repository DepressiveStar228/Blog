@BlogPost
Feature: Blog Post
  As a Logged In User of the application
  I want to create, edit and delete blog posts
  In order to share content with other users

  Background: User is logged in and is on Homepage
    Given I am a logged in user
    And I navigate to the "Home" page

  @SuccessfulLandOnAddPost
  Scenario: Successful landing on Add New Post page
    When I click on "+ Новий пост" link on the "Home" page
    Then I should land on the "Add New Post" page

  @SuccessfulAddPost
  Scenario Outline: Successful creation of a Blog Post
    When I click on "+ Новий пост" link on the "Home" page
    And I fill in "title" with "<title>"
    And I fill in "content" with "<content>"
    And I click on the "Зберегти" button
    Then I should land on the "Home" page
    And I should see "success" message as "Пост створено!"
    And I should see the new post listing on the Homepage
    Examples:
      | title      | content          |
      | My Post    | Some content     |

  @SuccessfulEditPost
  Scenario Outline: Successful editing of own Blog Post
    Given I have an existing blog post
    When I navigate to the post detail page
    And I click on the "Редагувати" link
    And I fill in "title" with "<title>"
    And I fill in "content" with "<content>"
    And I click on the "Зберегти" button
    Then I should land on the post detail page
    And I should see "success" message as "Пост оновлено!"
    And I should see the updated post content
    Examples:
      | title         | content             |
      | Updated Title | Updated content     |

  @SuccessfulDeletePost
  Scenario: Successful deletion of own Blog Post
    Given I have an existing blog post
    When I navigate to the post detail page
    And I click on the "Видалити" button
    And I confirm the delete dialog
    Then I should land on the "Home" page
    And I should see "success" message as "Пост видалено."
    And I should not see the deleted post on the Homepage

  @UnauthorizedEditPost
  Scenario: Unauthorized editing of another user's Blog Post
    Given I am viewing another user's blog post
    Then I should not see "Редагувати" link on the post detail page
    And I should not see "Видалити" button on the post detail page

  @EmptyPostList
  Scenario: Empty post list on Homepage
    Given there are no blog posts in the system
    When I navigate to the "Home" page
    Then I should see "Поки що немає жодного поста." message on the Homepage

@Comment
Feature: Add Comment on a Blog Post
  As a Logged In User of the application
  I want to write a comment to a Blog Post successfully
  In order to engage with other users' content

  Background: User is logged in and sees a post on Homepage
    Given I am a logged in user
    And I see a blog post listing on the Homepage

  @SuccessfulAddComment
  Scenario Outline: Successful Add Comment to a Blog Post
    When I click on a post title link on the "Home" page
    Then I should land on the "Post Detail" page
    When I fill in "comment" with "<comment>"
    And I click on the "Надіслати" button
    Then I should land on the "Post Detail" page
    And I should see the comment added to the post
    Examples:
      | comment            |
      | Great post!        |

  @SuccessfulDeleteOwnComment
  Scenario: Successful deletion of own comment
    Given I have an existing comment on a post
    When I navigate to the post detail page
    And I click on "Видалити коментар" button next to my comment
    Then I should land on the "Post Detail" page
    And I should see "success" message as "Коментар видалено."
    And I should not see the deleted comment on the page

  @SuccessfulDeleteCommentAsPostAuthor
  Scenario: Post author can delete any comment on own post
    Given I am the author of a blog post
    And another user has left a comment on my post
    When I navigate to the post detail page
    And I click on "Видалити коментар" button next to the comment
    Then I should land on the "Post Detail" page
    And I should see "success" message as "Коментар видалено."

  @UnauthorizedDeleteComment
  Scenario: Unauthorized deletion of another user's comment
    Given I am viewing a blog post with comments from other users
    Then I should not see "Видалити коментар" button next to other users' comments

  @GuestCannotComment
  Scenario: Guest user cannot leave a comment
    Given I am not logged in
    When I navigate to the "Post Detail" page
    Then I should see "Увійдіть, щоб залишити коментар." message
    And I should not see the comment form

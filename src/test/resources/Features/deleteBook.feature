Feature: Deleting a book
  As an Admin
  I want to delete a book
  in case it hase wrong details

  Scenario: Trying to delete a book that has wrong author name
    Given There is a book stored in database with wrong author name
    When  I try to delete that book
    Then  It must be removed from database
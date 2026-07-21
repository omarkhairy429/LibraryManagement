Feature: Adding new Book

  As a Library admin, I want to add a new book
  So that, users can view and borrow it

  @Valid
  Scenario: Saving a new book
    Given I have new book titled "Clean Code" and author name "Omar"
    When  I save the book in the database
    Then  I can get the book from the database
    Then  I should get the correct book details


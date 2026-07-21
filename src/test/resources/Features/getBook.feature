Feature: Getting a book by ID
  As I user I want to  get book by
  its ID, so that I can view the book
  details

  Scenario: Get book by ID
    Given A book exists in the database
    When  I retreive the book by id
    Then  I should see the book details

Feature: Borrow a book

  As a User , I want to borrow a book
  So that,    I can read it

  @Valid
  Scenario: Borrowing a book successfully
  Given I have a book in my database
  When  I try to borrow a book
  And   I have < 5 borrowed books
  And I don't have >= 3 overdue books
  Then New record should be created
  And   Ensure book is marked as Borrowed



  @Invalid
  Scenario: Trying to borrow more than five books
    Given I have 6 books stored in the database
    When  I borrow more than five books
    Then  I should get an exception can't borrow more than 5


  @Invalid
  Scenario: Tring to borrow a book while having 3 overdue books
    Given I have 4 books stored in my database
    When  I have 3 overdue books
    And   I try to borrow one more book
    Then  I should get an exception You are blocked

Feature: Return a book

  As a User , I want Return a book
  So that,    I can read other books later

  @Valid
  Scenario: I want to return a book
  Given There is a book stored in database
  When  I borrow the book
  And   I try to return it
  Then  Return date shouldn't be null
  And   Update the Record status to returned
  And   Update book availability to available
package com.orange.omar.LibraryManagement.models;

import java.util.Date;

public class BorrowRecord {
    private String borrowID;
    private String bookID;
    private String userID;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private String status;

    /***************************** Constructors *****************************/
    public BorrowRecord() {

    }


    public BorrowRecord(String borrowID, String bookID, Date borrowDate, String userID, Date dueDate, String status) {
        this.borrowID = borrowID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.userID = userID;
        this.dueDate = dueDate;
        this.status = status;
    }

    /***************************** Getters *****************************/
    public String getBorrowID() {
        return borrowID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getUserID() {
        return userID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    /***************************** Setters *****************************/
    public void setBorrowID(String borrowID) {
        this.borrowID = borrowID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

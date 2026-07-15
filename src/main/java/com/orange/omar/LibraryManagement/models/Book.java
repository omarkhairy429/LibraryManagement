package com.orange.omar.LibraryManagement.models;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;

    /***************************** Constructors *****************************/
    public Book() {

    }

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }
    /***************************** Getters *****************************/
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    /***************************** Setters *****************************/
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

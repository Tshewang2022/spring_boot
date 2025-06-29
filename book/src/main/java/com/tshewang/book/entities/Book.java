package com.tshewang.book.entities;

public class Book {
    private long id;
    private String title;
    private String author;
    private String category;
    private int rating;

    public Book(long id, String title, String author, String category, int rating){
        this.id= id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating){
        this.rating= rating;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return author;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }


}

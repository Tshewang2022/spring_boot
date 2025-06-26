package com.tshewang.book.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class BookRequest {
    @Size(min=1, max=30, message="Author is between 1 and 30 characters")
    private String title;

    @Size(min=1, max=30, message="Author is between 1 and 30 characters")
    private String author;

    @Size(min=1, max=30, message="Category is between 1 and 30 characters")
    private String category;

    @Max(value = 5, message = "Rating must be at least 1")
    @Min(value = 1, message = "Rating must not be more than 5")
    private int rating;

    public BookRequest(String title, int rating, String category, String author) {
        this.title = title;
        this.rating = rating;
        this.category = category;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}

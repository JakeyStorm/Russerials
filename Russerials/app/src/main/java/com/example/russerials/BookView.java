package com.example.russerials;

public class BookView {
    private String name;
    private String author;
    private String year;
    private String imageId;
    private String key;

    public BookView() {

    }

    public BookView(String name, String author, String year, String imageId, String key) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.imageId = imageId;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}

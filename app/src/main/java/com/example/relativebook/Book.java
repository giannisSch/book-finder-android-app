package com.example.relativebook;

import java.util.List;

public class Book {
    private String title;
    private List authors;
    private String publisher;
    private String publishedDate;
    private int pageCount;

    public Book(String title, List authors, String publisher, String publishedDate, int pageCount, String thumbnail, String description) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    private String thumbnail;
    private String description;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List getAuthors() {
        return authors;
    }

    public void setAuthors(List authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publicer) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publicedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.hfad.book;

public class Book {
    private String originalPublicationYear;
    private int ratingsCount;
    private double averageRating;

    public Book() {
    }

    public String getOriginalPublicationYear() {
        return originalPublicationYear;
    }

    public void setOriginalPublicationYear(String originalPublicationYear) {
        this.originalPublicationYear = originalPublicationYear;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}

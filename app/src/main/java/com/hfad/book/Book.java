package com.hfad.book;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;


@Xml(name = "work")
public class Book {

    @PropertyElement
    private String originalPublicationYear;
    @PropertyElement
    private int ratingsCount;
    @PropertyElement
    private double averageRating;
    @Element
    private BookInformation bookInformation;

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

    public BookInformation getBookInformation() {
        return bookInformation;
    }

    public void setBookInformation(BookInformation bookInformation) {
        this.bookInformation = bookInformation;
    }

    @Override
    public String toString() {
        return "Book{" +
                "originalPublicationYear='" + originalPublicationYear + '\'' +
                ", ratingsCount=" + ratingsCount +
                ", averageRating=" + averageRating +
                ", bookInformation=" + bookInformation +
                '}';
    }
}
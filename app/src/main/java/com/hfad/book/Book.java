package com.hfad.book;

import android.os.Parcel;
import android.os.Parcelable;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

import java.io.Serializable;


@Xml(name = "work")
public class Book{

    @PropertyElement(name = "original_publication_year")
    private String originalPublicationYear;
    @PropertyElement(name = "ratings_count")
    private int ratingsCount;
    @PropertyElement(name = "average_rating")
    private double averageRating;
    @Element(name = "best_book")
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

package com.hfad.book;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "work")
public class Book {
    @Element(name = "original_publication_year", required = false)
    private String originalPublicationYear;
    @Element(name = "ratings_count", required = false)
    private int ratingsCount;
    @Element(name = "average_rating", required = false)
    private double averageRating;
    @Element(name = "best_book", required = false)
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

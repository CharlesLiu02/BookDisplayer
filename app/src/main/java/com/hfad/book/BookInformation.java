package com.hfad.book;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "best_book")
public class BookInformation {
    @PropertyElement
    private String title;
    @Element
    private Author author;
    @PropertyElement(name = "image_url")
    private String imageUrl;

    public BookInformation() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BookInformation{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

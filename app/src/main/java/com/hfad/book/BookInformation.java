package com.hfad.book;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "best_book")
public class BookInformation {
    @Element(name = "title", required = false)
    private String title;
    @Element(name = "author", required = false)
    private Author author;
    @Element(name = "image_url", required = false)
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

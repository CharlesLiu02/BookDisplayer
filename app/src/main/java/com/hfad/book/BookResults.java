package com.hfad.book;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "results")
public class BookResults {
    @Element(name = "work")
    private List<Book> works;

    public BookResults() {
    }

    public List<Book> getWorks() {
        return works;
    }

    public void setWorks(List<Book> works) {
        this.works = works;
    }

    @Override
    public String toString() {
        return "BookResults{" +
                "works=" + works +
                '}';
    }
}

package com.hfad.book;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;


import java.util.List;

@Xml
public class Search{
    @Element
    private BookResults results;

    public Search() {
    }

    public BookResults getResults() {
        return results;
    }

    public void setResults(BookResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Search{" +
                "results=" + results +
                '}';
    }
}
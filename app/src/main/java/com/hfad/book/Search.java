package com.hfad.book;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "search")
public class Search{
    @Element(name = "results")
    private List<Book> results;

    public Search() {
    }

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Search{" +
                "results=" + results +
                '}';
    }
}
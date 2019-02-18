package com.hfad.book;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "GoodreadsResponse")
public class BookResponse {
    @Element(name = "search")
    private Search search;

    public BookResponse() {
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "search=" + search +
                '}';
    }
}

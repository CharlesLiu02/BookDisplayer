package com.hfad.book;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

//need to use annotations os that TickXml can match results to variables
@Xml(name = "GoodreadsResponse")
public class BookResponse {
    @Element
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

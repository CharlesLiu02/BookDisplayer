package com.hfad.book;

import java.util.List;

public class BookResponse {
    private int totalResults;
    private List<Book> results;

    public BookResponse() {
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "totalResults=" + totalResults +
                ", results=" + results +
                '}';
    }
}

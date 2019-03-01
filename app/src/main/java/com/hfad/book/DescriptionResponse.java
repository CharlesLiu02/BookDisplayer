package com.hfad.book;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "GoodreadsResponse")
public class DescriptionResponse {
    @Element(name = "book")
    private Description description;

    public DescriptionResponse() {
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}

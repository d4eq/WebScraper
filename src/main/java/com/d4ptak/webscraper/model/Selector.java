package com.d4ptak.webscraper.model;

import java.io.Serializable;

public class Selector implements Serializable {
    private String pagination;
    private String element;
    private String name;
    private String minimalPrice;
    private String imageUrl;

    public Selector(){}

    public Selector(String pagination, String element, String name, String minimalPrice, String imageUrl) {
        this.pagination = pagination;
        this.element = element;
        this.name = name;
        this.minimalPrice = minimalPrice;
        this.imageUrl = imageUrl;
    }

    public String getPagination() {
        return pagination;
    }

    public String getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

    public String getMinimalPrice() {
        return minimalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

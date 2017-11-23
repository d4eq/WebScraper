package com.d4ptak.webscraper.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {
    private String name;
    private BigDecimal minimalPrice;
    private String encodeImage;

    public Item() {}

    public Item(String name, BigDecimal minimalPrice, String encodeImage) {
        this.name = name;
        this.minimalPrice = minimalPrice;
        this.encodeImage = encodeImage;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinimalPrice() {
        return minimalPrice;
    }

    public String getEncodeImage() {
        return encodeImage;
    }
}

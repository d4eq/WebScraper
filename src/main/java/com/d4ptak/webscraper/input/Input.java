package com.d4ptak.webscraper.input;

import com.d4ptak.webscraper.model.Item;

import java.util.List;

public interface Input {
    List<Item> getItems(String url);
}

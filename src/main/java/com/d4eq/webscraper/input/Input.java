package com.d4eq.webscraper.input;

import com.d4eq.webscraper.model.Item;

import java.util.List;

public interface Input {
    List<Item> getItems(String url);
}

package com.d4eq.webscraper.output;

import com.d4eq.webscraper.model.Item;

import java.io.File;
import java.util.List;

public interface Output {
    void saveItems(List<Item> items, File outputFile);
}

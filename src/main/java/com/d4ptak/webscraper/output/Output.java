package com.d4ptak.webscraper.output;

import com.d4ptak.webscraper.model.Item;

import java.io.File;
import java.util.List;

public interface Output {
    void saveItems(List<Item> items, File outputFile);
}

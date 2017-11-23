package com.d4ptak.webscraper.input;

import com.d4ptak.webscraper.converter.Converter;
import com.d4ptak.webscraper.model.Selector;
import com.d4ptak.webscraper.scraper.Scraper;

public class InputFactory {
    public Input createInput(String inputType, Scraper scraper, Selector selector, Converter converter) {
        if (inputType.equalsIgnoreCase("ceneo-list") || inputType.equalsIgnoreCase("ceneo-box")) {
            return new CeneoInput(scraper, selector, converter);
        } else {
            throw new IllegalArgumentException("Invalid input case: " + inputType);
        }
    }
}

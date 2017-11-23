package com.d4ptak.webscraper.scraper;

import java.util.Set;

public interface Scraper {
    void initScraper(String url, String elementSelector, String paginationSelector);

    Set<Integer> getElementsKeys();

    String getNextPageUrl();

    String scrapeTextBySelector(int elementKey, String selector);

    String scrapeAttributeBySelector(int elementKey, String selector, String attribute);
}

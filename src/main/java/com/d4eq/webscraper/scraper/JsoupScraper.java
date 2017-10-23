package com.d4eq.webscraper.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class JsoupScraper implements Scraper {
    private final DocumentFetcher documentFetcher;
    private final Map<Integer, Element> elementsMap = new LinkedHashMap<>();
    private String nextPageUrl = "";

    public JsoupScraper(DocumentFetcher documentFetcher) {
        this.documentFetcher = documentFetcher;
    }

    public void initScraper(String url, String elementSelector, String paginationSelector) {
        elementsMap.clear();
        documentFetcher.fetchDocument(url).ifPresent(document -> {
            addElementsToElementsMap(document, elementSelector);
            setNextPageUrl(document, paginationSelector);
        });
    }

    public Set<Integer> getElementsKeys() {
        return elementsMap.keySet();
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String scrapeAttributeBySelector(int elementKey, String selector, String attribute) {
        return Optional.ofNullable(elementsMap.get(elementKey).select(selector).first())
                .map(element -> element.attr(attribute))
                .orElse("");
    }

    public String scrapeTextBySelector(int elementKey, String selector) {
        return Optional.ofNullable(elementsMap.get(elementKey).select(selector).first())
                .map(Element::text)
                .orElse("");
    }

    private void addElementsToElementsMap(Document document, String selector) {
        int key = 0;
        for (Element element : scrapeElementsBySelector(document, selector)) {
            elementsMap.put(key++, element);
        }
    }

    private Elements scrapeElementsBySelector(Document document, String selector) {
        return document.getElementsByClass(selector);
    }

    private void setNextPageUrl(Document document, String paginationSelector) {
        nextPageUrl = Optional.ofNullable(document.select(paginationSelector).first())
                .map(f -> f.attr("abs:href"))
                .orElse("");
    }
}

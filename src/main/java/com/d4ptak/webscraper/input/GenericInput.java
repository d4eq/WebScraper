package com.d4ptak.webscraper.input;

import com.d4ptak.webscraper.converter.Converter;
import com.d4ptak.webscraper.model.Item;
import com.d4ptak.webscraper.model.Selector;
import com.d4ptak.webscraper.scraper.Scraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenericInput implements Input {
    protected Logger logger = LogManager.getLogger(GenericInput.class);
    protected final Scraper scraper;
    protected final Selector selector;
    protected final Converter converter;

    public GenericInput(Scraper scraper, Selector selector, Converter converter) {
        this.scraper = scraper;
        this.selector = selector;
        this.converter = converter;
    }

    @Override
    public List<Item> getItems(String url) {
        List<Item> items = new ArrayList<>();
        String nextUrl = url;
        while (!nextUrl.isEmpty()) {
            scraper.initScraper(nextUrl, selector.getElement(), selector.getPagination());
            items.addAll(getItemsFromOnePage());
            nextUrl = scraper.getNextPageUrl();
        }
        logger.debug("Get {} items", items.size());
        return items;
    }

    protected List<Item> getItemsFromOnePage() {
        return scraper.getElementsKeys().stream()
                .mapToInt(elementKey -> elementKey)
                .mapToObj(this::newItem)
                .collect(Collectors.toList());
    }

    protected Item newItem(int elementKey) {
        return new Item(
                getName(elementKey),
                getMinimalPrice(elementKey),
                getConvertedImage(elementKey)
        );
    }

    protected String getName(int elementKey) {
        return scraper.scrapeTextBySelector(elementKey, selector.getName());
    }

    protected BigDecimal getMinimalPrice(int elementKey) {
        return new BigDecimal(scraper.scrapeTextBySelector(elementKey, selector.getMinimalPrice()));
    }

    protected String getConvertedImage(int elementKey) {
        return converter.convert(getImageUrl(elementKey));
    }

    protected String getImageUrl(int elementKey) {
        return scraper.scrapeAttributeBySelector(elementKey, selector.getImageUrl(), "src");
    }
}

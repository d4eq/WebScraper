package com.d4ptak.webscraper.input;

import com.d4ptak.webscraper.converter.Converter;
import com.d4ptak.webscraper.model.Selector;
import com.d4ptak.webscraper.scraper.Scraper;
import org.apache.logging.log4j.LogManager;

import java.math.BigDecimal;

public class CeneoInput extends GenericInput {

    public CeneoInput(Scraper scraper, Selector selector, Converter converter) {
        super(scraper, selector, converter);
        super.logger = LogManager.getLogger(CeneoInput.class);
    }

    @Override
    protected BigDecimal getMinimalPrice(int elementKey) {
        return new BigDecimal(getMinimalPriceValue(elementKey)
                + "." + getMinimalPricePenny(elementKey).replace(",", ""));
    }

    private String getMinimalPriceValue(int elementKey) {
        return scraper.scrapeTextBySelector(elementKey, selector.getMinimalPrice() + " > span.value");
    }

    private String getMinimalPricePenny(int elementKey) {
        return scraper.scrapeTextBySelector(elementKey, selector.getMinimalPrice() + " > span.penny");
    }

    @Override
    protected String getImageUrl(int elementKey) {
        String imageUrl = scraper.scrapeAttributeBySelector(elementKey, selector.getImageUrl(), "data-original");
        if (imageUrl.isEmpty()) {
            imageUrl = scraper.scrapeAttributeBySelector(elementKey, selector.getImageUrl(), "src");
        }
        return imageUrl;
    }
}

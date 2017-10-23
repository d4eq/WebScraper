package com.d4eq.webscraper.validator;

import com.d4eq.webscraper.scraper.DocumentFetcher;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {
    private final Logger logger = LogManager.getLogger(DocumentFetcher.class);

    public boolean isValidUrl(String url)  {
        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        boolean isValid = urlValidator.isValid(url);
        logger.debug("Valid URL: {} is {}", url, isValid);
        return isValid;
    }
}

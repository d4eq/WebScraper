package com.d4ptak.webscraper.scraper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Optional;

public class DocumentFetcher {
    private final Logger logger = LogManager.getLogger(DocumentFetcher.class);

    public Optional<Document> fetchDocument(String url) {
        logger.info("Fetching the page: {}", url);
        try {
            return Optional.ofNullable(Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0")
                    .get());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }
}

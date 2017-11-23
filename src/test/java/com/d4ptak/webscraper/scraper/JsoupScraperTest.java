package com.d4ptak.webscraper.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JsoupScraperTest {
    private JsoupScraper jsoupScraper;

    @Before
    public void setUp() throws IOException {
        Document document = Jsoup.parse(new File("src/test/resources/ceneo1.html"), "utf-8");
        DocumentFetcher documentFetcher = mock(DocumentFetcher.class);
        when(documentFetcher.fetchDocument(anyString())).thenReturn(Optional.of(document));
        jsoupScraper = new JsoupScraper(documentFetcher);
        jsoupScraper.initScraper(anyString(), "element", "a.pagination-next");
    }

    @Test
    public void shouldInitElementsMap() {
        // when
        int elementMapSize = jsoupScraper.getElementsKeys().size();
        // then
        assertThat(elementMapSize, is(2));
    }

    @Test
    public void shouldInitNextPageUrl() {
        // when
        String nextPageUrl = jsoupScraper.getNextPageUrl();
        // then
        assertEquals("http://www.test.com/page2.htm", nextPageUrl);
    }

    @Test
    public void shouldScrapeTextBySelector() {
        // when
        String textBySelector = jsoupScraper.scrapeTextBySelector(0, "strong.name");
        // then
        assertEquals("name11", textBySelector);
    }

    @Test
    public void shouldScrapeAttributeBySelector() {
        //when
        String attributeBySelector = jsoupScraper.scrapeAttributeBySelector(0, "div.image > img", "src");
        // then
        assertEquals("//www.test.com/image11.jpg", attributeBySelector);
    }
}
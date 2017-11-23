package com.d4ptak.webscraper.input;

import com.d4ptak.webscraper.converter.Converter;
import com.d4ptak.webscraper.model.Item;
import com.d4ptak.webscraper.model.Selector;
import com.d4ptak.webscraper.scraper.DocumentFetcher;
import com.d4ptak.webscraper.scraper.JsoupScraper;
import com.d4ptak.webscraper.scraper.Scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CeneoInputTest {
    private static final String URL_1 = "http://www.test.com/page1.htm";
    private static final String URL_2 = "http://www.test.com/page2.htm";
    private CeneoInput ceneoInput;

    @Before
    public void setUp() throws IOException {
        Document document1 = Jsoup.parse(new File("src/test/resources/ceneo1.html"), "utf-8");
        Document document2 = Jsoup.parse(new File("src/test/resources/ceneo2.html"), "utf-8");

        DocumentFetcher documentFetcher = mock(DocumentFetcher.class);
        when(documentFetcher.fetchDocument(URL_1)).thenReturn(Optional.of(document1));
        when(documentFetcher.fetchDocument(URL_2)).thenReturn(Optional.of(document2));

        Scraper scraper = new JsoupScraper(documentFetcher);

        Selector selector = mock(Selector.class);
        when(selector.getPagination()).thenReturn("a.pagination-next");
        when(selector.getElement()).thenReturn("element");
        when(selector.getName()).thenReturn("strong.name");
        when(selector.getMinimalPrice()).thenReturn("span.price");
        when(selector.getImageUrl()).thenReturn("div.image > img");

        Converter converter = mock(Converter.class);
        when(converter.convert(anyString())).thenReturn("=AAAAA");

        ceneoInput = new CeneoInput(scraper, selector, converter);
    }

    @Test
    public void shouldGetItems()  {
        // when
        List<Item> items = ceneoInput.getItems(URL_1);

        // then
        assertThat(items.size(), is(4));

        assertEquals("name11", items.get(0).getName());
        assertEquals(new BigDecimal("1.1"), items.get(0).getMinimalPrice());
        assertEquals("=AAAAA", items.get(0).getEncodeImage());
    }
}
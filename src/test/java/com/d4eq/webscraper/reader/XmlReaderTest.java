package com.d4eq.webscraper.reader;

import com.d4eq.webscraper.model.Selector;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class XmlReaderTest {

    @Test
    public void shouldReadSelector() {
        // given
        Selector expectedSelector = new Selector("p", "e", "n", "mp", "iu");

        String testXml = "/selector-test.xml";
        XmlReader xmlReader = new XmlReader();

        // when
        Selector actualSelector = xmlReader.mapObjectFromXml(testXml).get();

        // then
        assertEquals(expectedSelector.getPagination(), actualSelector.getPagination());
        assertEquals(expectedSelector.getElement(), actualSelector.getElement());
        assertEquals(expectedSelector.getName(), actualSelector.getName());
        assertEquals(expectedSelector.getMinimalPrice(), actualSelector.getMinimalPrice());
        assertEquals(expectedSelector.getImageUrl(), actualSelector.getImageUrl());
    }

    @Test
    public void shouldReadUnrecognizedField() {
        // given
        String testXml = "/selector-test-unrecognized.xml";
        XmlReader xmlReader = new XmlReader();

        // when
        Optional<Selector> actualSelector = xmlReader.mapObjectFromXml(testXml);

        // then
        assertThat(actualSelector.isPresent(), is(false));
    }

    @Test
    public void shouldReadSelectorFromNotFileExists() {
        // given
        String testXml = "/not-exists.xml";
        XmlReader xmlReader = new XmlReader();

        // when
        Optional<Selector> actualSelector = xmlReader.mapObjectFromXml(testXml);

        // then
        assertThat(actualSelector.isPresent(), is(false));
    }


}
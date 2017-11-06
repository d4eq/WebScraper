package com.d4eq.webscraper.reader;

import com.d4eq.webscraper.model.Selector;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class XmlReaderTest {

    @Test
    public void shouldReadSelector() {
        // given
        Selector expectedSelector = new Selector("p","e","n","mp","iu");

        File testXml = new File("src/test/resources/selector-test.xml");
        XmlReader xmlReader = new XmlReader();

        // when
        Selector actualSelector = xmlReader.mapObjectFromXml(testXml);

        // then
        assertEquals(expectedSelector.getPagination(), actualSelector.getPagination());
        assertEquals(expectedSelector.getElement(), actualSelector.getElement());
        assertEquals(expectedSelector.getName(), actualSelector.getName());
        assertEquals(expectedSelector.getMinimalPrice(), actualSelector.getMinimalPrice());
        assertEquals(expectedSelector.getImageUrl(), actualSelector.getImageUrl());
    }
}
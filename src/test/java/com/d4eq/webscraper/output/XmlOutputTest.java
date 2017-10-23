package com.d4eq.webscraper.output;

import com.d4eq.webscraper.model.Item;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class XmlOutputTest {

    @Test
    public void shouldSaveCorrectlyItemsToXmlFile() throws IOException {
        List<Item> expectedItems = new ArrayList<>(Arrays.asList(
                new Item("name1", new BigDecimal("1.1"), "image1="),
                new Item("name2", new BigDecimal("1.2"), "image2="),
                new Item("name3", new BigDecimal("1.3"), "image3=")
        ));
        File outputFile = new File("src/test/resources/output.xml");

        XmlOutput xmlOutput = new XmlOutput();
        xmlOutput.saveItems(expectedItems, outputFile);

        XmlMapper xmlMapper = new XmlMapper();
        JavaType javaType = xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, Item.class);
        List<Item> actualItem = xmlMapper.readValue(outputFile, javaType);

        assertEquals(expectedItems.size(), actualItem.size());

        assertEquals(expectedItems.get(0).getName(), actualItem.get(0).getName());
        assertEquals(expectedItems.get(1).getMinimalPrice(), actualItem.get(1).getMinimalPrice());
        assertEquals(expectedItems.get(2).getEncodeImage(), actualItem.get(2).getEncodeImage());
    }
}
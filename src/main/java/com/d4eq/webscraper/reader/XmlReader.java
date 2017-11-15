package com.d4eq.webscraper.reader;

import com.d4eq.webscraper.model.Selector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class XmlReader {
    private final Logger logger = LogManager.getLogger(XmlReader.class);

    public Selector mapObjectFromXml(String resourceName) {
        ObjectMapper xmlMapper = new XmlMapper();
        try (InputStream is = getClass().getResourceAsStream(resourceName)) {
            return xmlMapper.readValue(is, Selector.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}

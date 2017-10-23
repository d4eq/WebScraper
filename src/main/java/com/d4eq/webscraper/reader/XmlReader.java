package com.d4eq.webscraper.reader;

import com.d4eq.webscraper.model.Selector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class XmlReader {
    private final Logger logger = LogManager.getLogger(XmlReader.class);
    public Selector mapObjectFromXml(File profileXml) {
        ObjectMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(profileXml, Selector.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}

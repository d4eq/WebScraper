package com.d4ptak.webscraper.output;

import com.d4ptak.webscraper.model.Item;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlOutput implements Output {
    private final Logger logger = LogManager.getLogger(XmlOutput.class);

    @Override
    public void saveItems(List<Item> items, File outputFile) {
        logger.info("Save {} items to {}", items.size(), outputFile.getName());
        XmlMapper xmlMapper = newXmlMpper();
        try {
            xmlMapper.writeValue(outputFile, items);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private XmlMapper newXmlMpper() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper;
    }
}

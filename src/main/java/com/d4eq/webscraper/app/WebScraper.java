package com.d4eq.webscraper.app;

import com.d4eq.webscraper.cli.CliParser;
import com.d4eq.webscraper.converter.Converter;
import com.d4eq.webscraper.converter.ImageToBase64Converter;
import com.d4eq.webscraper.downloader.Downloader;
import com.d4eq.webscraper.encode.Base64Encoder;
import com.d4eq.webscraper.encode.Encoder;
import com.d4eq.webscraper.input.Input;
import com.d4eq.webscraper.input.InputFactory;
import com.d4eq.webscraper.model.Item;
import com.d4eq.webscraper.model.Selector;
import com.d4eq.webscraper.output.Output;
import com.d4eq.webscraper.output.OutputFactory;
import com.d4eq.webscraper.reader.XmlReader;
import com.d4eq.webscraper.scraper.DocumentFetcher;
import com.d4eq.webscraper.scraper.JsoupScraper;
import com.d4eq.webscraper.scraper.Scraper;
import com.d4eq.webscraper.validator.Validator;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.util.List;
import java.util.Map;

public class WebScraper {
    private final Logger logger = LogManager.getLogger(WebScraper.class);

    public void cliParse(String[] args) {
        CliParser cliParser = new CliParser();
        try {
            init(cliParser.parse(args));
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
    }

    private void init(Map<String, String> args) {
        if(args.containsKey("debug")) {
            setDebugMode();
        }

        Validator validator = new Validator();
        if (validator.isValidUrl(args.get("url"))) {
            String profile = setDefaultProfile(args.get("profile"));
            String outputType = setDefaultOutputType(args.get("outputType"));
            File outputFile = setOutputFile(profile);

            Scraper scraper = getScraper();
            Selector selector = getSelector(profile);
            Converter converter = getConverter();

            Input input = getInput(profile, scraper, selector, converter);
            Output output = getOutput(outputType);

            run(args.get("url"), input, output, outputFile);
        } else {
            logger.error("Invalid URL: {}", args.get("url"));
        }
    }

    private void setDebugMode() {
        Configurator.setRootLevel(Level.DEBUG);
    }

    private String setDefaultProfile(String profile) {
        return profile.isEmpty() ? "ceneo-list" : profile;
    }

    private String setDefaultOutputType(String outputType) {
        return outputType.isEmpty() ? "xml" : outputType;
    }

    private void run(String url, Input input, Output output, File outputFile) {
        List<Item> items = input.getItems(url);
        if (items.size() > 0) {
            output.saveItems(items, outputFile);
        } else {
            logger.info("No items");
        }
    }

    private Scraper getScraper() {
        DocumentFetcher documentFetcher = new DocumentFetcher();
        return new JsoupScraper(documentFetcher);
    }

    private Selector getSelector(String profile) {
        XmlReader xmlReader = new XmlReader();
        File profileXml = setProfileFile(profile);
        return xmlReader.mapObjectFromXml(profileXml);
    }

    private Converter getConverter() {
        Downloader downloader = new Downloader();
        Encoder encoder = new Base64Encoder();
        return new ImageToBase64Converter(downloader, encoder);
    }

    private Input getInput(String profile, Scraper scraper, Selector selector, Converter converter) {
        InputFactory inputFactory = new InputFactory();
        return inputFactory.createInput(profile, scraper, selector, converter);
    }

    private Output getOutput(String outputType) {
        OutputFactory outputFactory = new OutputFactory();
        return outputFactory.createOutput(outputType);
    }


    private File setProfileFile(String profile) {
        return new File("/profiles/" + profile + ".xml");
    }

    private File setOutputFile(String profile) {
        return new File(profile + "-output.xml");
    }
}

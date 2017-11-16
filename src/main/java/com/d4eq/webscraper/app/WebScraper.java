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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WebScraper {
    private final Logger logger = LogManager.getLogger(WebScraper.class);
    Map<String, String> args = new HashMap<>();

    public void cliParse(String[] args) {
        CliParser cliParser = new CliParser();
        try {
            this.args = cliParser.parse(args);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
    }

    public void init() {
        if (args.containsKey("debug")) {
            setDebugMode();
        }

        Validator validator = new Validator();
        if (validator.isValidUrl(args.get("url"))) {
            initSelector();
        } else {
            logger.error("Invalid URL: {}", args.get("url"));
        }
    }

    private void initSelector() {
        getSelector().ifPresent(selector -> {
            initInput(selector);
        });
    }

    private void initInput(Selector selector) {
        Scraper scraper = getScraper();
        Converter converter = getConverter();
        getInput(scraper, selector, converter).ifPresent(input -> {
            initOutput(input);
        });
    }

    private void initOutput(Input input) {
        getOutput().ifPresent(output -> {
            run(input, output);
        });
    }

    private void run(Input input, Output output) {
        List<Item> items = input.getItems(args.get("url"));
        if (items.size() > 0) {
            File outputFile = getOutputFile();
            output.saveItems(items, outputFile);
        } else {
            logger.info("No items");
        }
    }

    private Scraper getScraper() {
        DocumentFetcher documentFetcher = new DocumentFetcher();
        return new JsoupScraper(documentFetcher);
    }

    private Optional<Selector> getSelector() {
        String profile = getProfile();
        XmlReader xmlReader = new XmlReader();
        String resourceName = getResourceName(profile);
        return xmlReader.mapObjectFromXml(resourceName);
    }

    private Converter getConverter() {
        Downloader downloader = new Downloader();
        Encoder encoder = new Base64Encoder();
        return new ImageToBase64Converter(downloader, encoder);
    }

    private Optional<Input> getInput(Scraper scraper, Selector selector, Converter converter) {
        InputFactory inputFactory = new InputFactory();
        String profile = getProfile();
        try {
            return Optional.of(inputFactory.createInput(profile, scraper, selector, converter));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Output> getOutput() {
        OutputFactory outputFactory = new OutputFactory();
        String outputType = getOutputType();
        try {
            return Optional.of(outputFactory.createOutput(outputType));
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    private void setDebugMode() {
        Configurator.setRootLevel(Level.DEBUG);
    }

    private String getProfile() {
        return args.get("profile").isEmpty() ? "ceneo-list" : args.get("profile");
    }

    private String getOutputType() {
        return args.get("outputType").isEmpty() ? "xml" : args.get("outputType");
    }

    private String getResourceName(String profile) {
        return "/profiles/" + profile + ".xml";
    }

    private File getOutputFile() {
        String profile = getProfile();
        String outputType = getOutputType();
        return new File(profile + "-output." + outputType);
    }
}

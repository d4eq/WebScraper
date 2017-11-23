package com.d4ptak.webscraper.cli;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class CliParser {
    private final Options options = new Options();

    public CliParser() {
        initOptions();
    }

    private void initOptions() {
        options.addOption("h", "help", false, "print this message");
        options.addOption("u", "url", true, "source url (required)");
        options.addOption("p", "profile", true, "input profile name: ceneo-list (default), ceneo-box");
        options.addOption("t", "type", true, "output type: xml (default)");
        options.addOption("d", "debug", false, "debug mode");
    }

    public Map parse(String[] args) throws ParseException {
        Map<String, String> parsedArgs = new HashMap<>();
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("h")) {
            help();
        }

        if (line.hasOption("u")) {
            parsedArgs.put("url", line.getOptionValue("u"));
        }

        if (line.hasOption("d")) {
            parsedArgs.put("debug", "");
        }

        String profile = "";
        if (line.hasOption("p")) {
           profile = line.getOptionValue("p");
        }
        parsedArgs.put("profile", profile);

        String outputType = "";
        if (line.hasOption("t")) {
            outputType = line.getOptionValue("t");
        }
        parsedArgs.put("outputType", outputType);

        if (isNotRequiredOptions(parsedArgs)) {
            throw new ParseException("No required option: url");
        } else {
            return parsedArgs;
        }
    }

    private boolean isNotRequiredOptions(Map<String, String> parsedArgs) {
        return parsedArgs.size() == 0 || !parsedArgs.containsKey("url");
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("webscraper", options);
        System.exit(0);
    }
}

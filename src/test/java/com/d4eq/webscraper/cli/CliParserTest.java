package com.d4eq.webscraper.cli;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class CliParserTest {
    private CliParser cliParser;

    @Before
    public void setUp() {
        cliParser = new CliParser();
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void shouldUnrecognizedOption() throws ParseException {
        String[] args = { "-b" };
        cliParser.parse(args);
    }

    @Test
    public void shouldGetCorrectlyUrlOption() throws ParseException {
        String[] args = { "-uhttp://www.test.com" };
        Map<String, String> parsedArgs = cliParser.parse(args);
        assertEquals("http://www.test.com", parsedArgs.get("url"));
    }

    @Test(expected = ParseException.class)
    public void shouldGetMissingUrlOption() throws ParseException {
        String[] args = { "" };
        cliParser.parse(args);
    }

    @Test(expected = MissingArgumentException.class)
    public void shouldGetMissingArgumentUrlOption() throws ParseException {
        String[] args = { "-u" };
        cliParser.parse(args);
    }

    @Test
    public void shouldGetCorrectlyDebugAndRequiredUrlOption() throws ParseException {
        String[] args = { "-d", "-uhttp://www.test.com" };
        Map<String, String> parsedArgs = cliParser.parse(args);
        assertEquals("", parsedArgs.get("debug"));
    }

    @Test
    public void shouldGetCorrectlyProfileAndRequiredUrlOption() throws ParseException {
        String[] args = { "-pceneo", "-uhttp://www.test.com" };
        Map<String, String> parsedArgs = cliParser.parse(args);
        assertEquals("ceneo", parsedArgs.get("profile"));
    }

    @Test(expected = MissingArgumentException.class)
    public void shouldGetMissingArgumentProfileAndRequiredUrlOption() throws ParseException {
        String[] args = { "-p", "-uhttp://www.test.com" };
        cliParser.parse(args);
    }

    @Test
    public void shouldGetCorrectlyOutputTypeAndRequiredUrlOption() throws ParseException {
        String[] args = { "-txml", "-uhttp://www.test.com" };
        Map<String, String> parsedArgs = cliParser.parse(args);
        assertEquals("xml", parsedArgs.get("outputType"));
    }

    @Test(expected = MissingArgumentException.class)
    public void shouldGetMissingArgumentOutputTypeAndRequiredUrlOption() throws ParseException {
        String[] args = { "-t", "-uhttp://www.test.com" };
        cliParser.parse(args);
    }
}
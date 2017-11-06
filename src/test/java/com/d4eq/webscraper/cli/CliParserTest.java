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
    public void shouldUsageUnrecognizedOption() throws ParseException {
        // given
        String[] args = { "-b" };
        // when
        cliParser.parse(args);
    }

    @Test
    public void shouldUsageCorrectUrlOption() throws ParseException {
        // given
        String[] args = { "-uhttp://www.test.com" };
        // when
        Map<String, String> parsedArgs = cliParser.parse(args);
        // then
        assertEquals("http://www.test.com", parsedArgs.get("url"));
    }

    @Test(expected = ParseException.class)
    public void shouldUsageMissingUrlOption() throws ParseException {
        // given
        String[] args = { "" };
        // when
        cliParser.parse(args);
    }

    @Test(expected = MissingArgumentException.class)
    public void shouldUsageMissingArgumentUrlOption() throws ParseException {
        // given
        String[] args = { "-u" };
        // when
        cliParser.parse(args);
    }

    @Test
    public void shouldUsageCorrectDebugAndRequiredUrlOption() throws ParseException {
        // given
        String[] args = { "-d", "-uhttp://www.test.com" };
        // when
        Map<String, String> parsedArgs = cliParser.parse(args);
        // then
        assertEquals("", parsedArgs.get("debug"));
    }

    @Test
    public void shouldUsageCorrectProfileAndRequiredUrlOption() throws ParseException {
        // given
        String[] args = { "-pceneo", "-uhttp://www.test.com" };
        // when
        Map<String, String> parsedArgs = cliParser.parse(args);
        // then
        assertEquals("ceneo", parsedArgs.get("profile"));
    }

    @Test(expected = MissingArgumentException.class)
    public void shouldUsageMissingArgumentProfileAndRequiredUrlOption() throws ParseException {
        // given
        String[] args = { "-p", "-uhttp://www.test.com" };
        // when
        cliParser.parse(args);
    }

    @Test
    public void shouldUsageCorrectOutputTypeAndRequiredUrlOption() throws ParseException {
        // given
        String[] args = { "-txml", "-uhttp://www.test.com" };
        // when
        Map<String, String> parsedArgs = cliParser.parse(args);
        // then
        assertEquals("xml", parsedArgs.get("outputType"));
    }

    @Test(expected = MissingArgumentException.class)
    public void shouldUsageMissingArgumentOutputTypeAndRequiredUrlOption() throws ParseException {
        // given
        String[] args = { "-t", "-uhttp://www.test.com" };
        // when
        cliParser.parse(args);
    }
}
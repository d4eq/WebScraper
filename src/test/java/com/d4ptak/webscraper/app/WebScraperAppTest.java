package com.d4ptak.webscraper.app;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class WebScraperAppTest {

    @Test
    public void shouldGetInvalidProfileCase() throws IOException {
        // given
        String error = "";
        String cmd = "java -jar target/WebScraper-1.0.jar -u http://www.test.com -p other";
        // when
        Process process = Runtime.getRuntime().exec(cmd);
        // then
        try(InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream())){
            error = IOUtils.toString(inputStreamReader).trim();
        }
        assertEquals("[ERROR] Profile '/profiles/other.xml' is not found", error);
    }

    @Test
    public void shouldGetInvalidOutputType() throws IOException {
        // given
        String error = "";
        String cmd = "java -jar target/WebScraper-1.0.jar -u http://www.test.com -t txt";
        // when
        Process process = Runtime.getRuntime().exec(cmd);
        // then
        try(InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream())){
            error = IOUtils.toString(inputStreamReader).trim();
        }
        assertEquals("[ERROR] Invalid output file type: txt", error);
    }

}
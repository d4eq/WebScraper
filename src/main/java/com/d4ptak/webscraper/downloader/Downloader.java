package com.d4ptak.webscraper.downloader;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class Downloader {
    private final Logger logger = LogManager.getLogger(Downloader.class);

    public byte[] downloadAsByteArray(String imageUrl) {
        imageUrl = imageUrl.startsWith("//") ? "http:" + imageUrl : imageUrl;
        logger.debug("Download from URL: {}", imageUrl);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(imageUrl);
        try (InputStream content = httpClient.execute(httpGet).getEntity().getContent()) {
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new byte[0];
        }
    }
}

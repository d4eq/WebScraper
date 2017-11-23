package com.d4ptak.webscraper.encode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;

public class Base64Encoder implements Encoder {
    private final Logger logger = LogManager.getLogger(Base64Encoder.class);

    @Override
    public String encode(byte[] bytes) {
        logger.debug("Base64 Encode: {}", bytes.length);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

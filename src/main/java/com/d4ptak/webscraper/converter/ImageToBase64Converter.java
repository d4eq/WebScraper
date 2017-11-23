package com.d4ptak.webscraper.converter;

import com.d4ptak.webscraper.downloader.Downloader;
import com.d4ptak.webscraper.encode.Encoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageToBase64Converter implements Converter {
    private final Logger logger = LogManager.getLogger(ImageToBase64Converter.class);
    private final Downloader downloader;
    private final Encoder encoder;

    public ImageToBase64Converter(Downloader downloader, Encoder encoder) {
        this.downloader = downloader;
        this.encoder = encoder;
    }

    @Override
    public String convert(String imageUrl) {
        logger.debug("Convert the image: {} ...", imageUrl);
        return encoder.encode(getImageAsByte(imageUrl));
    }

    private byte[] getImageAsByte(String imageUrl) {
        return downloader.downloadAsByteArray(imageUrl);
    }
}

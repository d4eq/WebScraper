package com.d4eq.webscraper.converter;

import com.d4eq.webscraper.downloader.Downloader;
import com.d4eq.webscraper.encode.Encoder;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ImageToBase64ConverterTest {

    @Test
    public void shouldConvertCorrectlyImageToBase64() {
        Downloader downloader = mock(Downloader.class);
        Encoder encoder = mock(Encoder.class);

        when(downloader.downloadAsByteArray(anyString())).thenReturn(new byte[5]);
        when(encoder.encode(new byte[5])).thenReturn("AAAAAAA=");

        ImageToBase64Converter converter = new ImageToBase64Converter(downloader, encoder);

        assertEquals("AAAAAAA=", converter.convert(anyString()));
    }
}
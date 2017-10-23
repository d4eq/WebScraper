package com.d4eq.webscraper.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        validator = new Validator();
    }

    @Test
    public void shouldValidCorrectlyUrl() {
        assertTrue(validator.isValidUrl("http://www.test.com"));
        assertTrue(validator.isValidUrl("https://www.test.com"));
        assertTrue(validator.isValidUrl("http://www.test.com/index.htm"));
        assertTrue(validator.isValidUrl("https://www.test.com/index.htm"));
    }

    @Test
    public void shouldInvalidCorrectlyUrl() {
        assertFalse(validator.isValidUrl("www"));
        assertFalse(validator.isValidUrl("www.test"));
        assertFalse(validator.isValidUrl("http://"));
        assertFalse(validator.isValidUrl("htt://www.test.com"));
        assertFalse(validator.isValidUrl("http://www.test"));
    }

}
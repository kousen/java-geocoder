package com.kousenit.services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddressEncoderTest {
    private AddressEncoder encoder = new AddressEncoder();

    @Test
    public void tryCatchBlock() {
        String correct = "The+string,%C3%BC%40foo-bar";
        String encoded = encoder.getEncodedAddress_usingTryCatch("The string", "ü@foo-bar");
        assertEquals(correct, encoded);
    }

    @Test
    public void extractedMethod() {
        String correct = "The+string,%C3%BC%40foo-bar";
        String encoded = encoder.getEncodedAddress_usingExtractedMethod("The string", "ü@foo-bar");
        assertEquals(correct, encoded);
    }

    @Test
    public void wrappedException() {
        String correct = "The+string,%C3%BC%40foo-bar";
        String encoded = encoder.getEncodedAddress_usingWrapper("The string", "ü@foo-bar");
        assertEquals(correct, encoded);
    }
}

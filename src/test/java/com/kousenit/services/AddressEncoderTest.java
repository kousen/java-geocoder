package com.kousenit.services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddressEncoderTest {
    private AddressEncoder encoder = new AddressEncoder();

    @Test
    public void tryCatchBlock() {
        String correct = "Gedenkst%C3%A4tte+Berliner+Mauer,Bernauer+Stra%C3%9Fe+111,Berlin,Deutschland";
        String encoded = encoder.getEncodedAddress_usingTryCatch(
                "Gedenkstätte Berliner Mauer", "Bernauer Straße 111", "Berlin", "Deutschland");
        assertEquals(correct, encoded);
    }

    @Test
    public void extractedMethod() {
        String correct = "St.+Patrick%27s+Old+Cathedral,Mulberry+Street,Manhattan,New+York+City";
        String encoded = encoder.getEncodedAddress_usingExtractedMethod(
                "St. Patrick's Old Cathedral", "Mulberry Street", "Manhattan", "New York City");
        assertEquals(correct, encoded);
    }

    @Test
    public void wrappedException() {
        String correct = "O%27Reilly+Media,1005+Gravenstein+Hwy+N,Sebastopol,CA";
        String encoded = encoder.getEncodedAddress_usingWrapper(
                "O'Reilly Media", "1005 Gravenstein Hwy N", "Sebastopol", "CA");
        assertEquals(correct, encoded);
    }
}

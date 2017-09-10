package com.kousenit.services;

import com.kousenit.entities.Site;
import org.junit.Test;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class GeocoderServiceTest {
    private GeocoderService service = new GeocoderService();
    private Logger logger = Logger.getLogger(GeocoderServiceTest.class.getSimpleName());

    @Test
    public void fenwayPark() {
        Optional<Site> optionalSite = service.getLatLng("Fenway Park",
                "4 David Ortiz Drive",
                "Boston",
                "MA");
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            logger.info(site::toString);
            assertEquals(42.347, site.getLatitude(), 0.01);
            assertEquals(-71.097, site.getLongitude(), 0.01);
        }
    }

    @Test
    public void berlinWallMonument() {
        Optional<Site> optionalSite = service.getLatLng("Gedenkstätte Berliner Mauer",
                "Bernauer Straße 111",
                "Berlin",
                "Deutschland");
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            logger.info(site::toString);
            assertEquals(52.535, site.getLatitude(), 0.01);
            assertEquals(13.390, site.getLongitude(), 0.01);
        }
    }

    @Test
    public void oldStPatricksCathedral() {
        Optional<Site> optionalSite = service.getLatLng("St. Patrick's Old Cathedral",
                "Mulberry Street",
                "Manhattan",
                "New York City");
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            logger.info(site::toString);
            assertEquals(40.724, site.getLatitude(), 0.01);
            assertEquals(-73.995, site.getLongitude(), 0.01);
        }
    }

    @Test
    public void oreillyHomeOffice() {
        Optional<Site> optionalSite = service.getLatLng("O'Reilly Media",
                "1005 Gravenstein Hwy N",
                "Sebastopol",
                "CA");
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            logger.info(site::toString);
            assertEquals(38.412, site.getLatitude(), 0.01);
            assertEquals(-122.841, site.getLongitude(), 0.01);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addressDoesntExist() {
        service.getLatLng("King's Landing", "Westeros");
    }
}
package com.kousenit.services;

import com.kousenit.entities.Site;
import org.junit.Test;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeocoderServiceTest {
    private GeocoderService service = new GeocoderService();
    private Logger logger = Logger.getLogger(GeocoderServiceTest.class.getSimpleName());

    @Test
    public void getLatLngWithoutStreet() throws Exception {
        Optional<Site> optionalSite = service.getLatLng("Boston", "MA");
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            logger.info(site::toString);
            assertEquals(42.36, site.getLatitude(), 0.01);
            assertEquals(-71.06, site.getLongitude(), 0.01);
        }
    }

    @Test
    public void getLatLngWithStreet() throws Exception {
        Optional<Site> optionalSite = service.getLatLng("1600 Ampitheatre Parkway",
                "Mountain View", "CA");
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            logger.info(site::toString);
            assertEquals(37.42, site.getLatitude(), 0.01);
            assertEquals(-122.08, site.getLongitude(), 0.01);
        }
    }

    @Test
    public void emptyAddress() {
        Optional<Site> optionalSite = service.getLatLng();
        assertTrue(!optionalSite.isPresent());
    }
}
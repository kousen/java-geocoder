package com.kousenit.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kousenit.entities.Site;
import com.kousenit.json.GeocoderResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class GeocoderService {
    private static final String BASE = "https://maps.googleapis.com/maps/api/geocode/json";
    private Gson gson;
    private OkHttpClient client = new OkHttpClient();
    private AddressEncoder encoder = new AddressEncoder();

    public GeocoderService() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public Optional<Site> getLatLng(String... address) {
        Site site = null;
        String geocoderUrl = String.format("%s?address=%s", BASE, encoder.getEncodedAddress_extractedMethod(address));
        Request request = new Request.Builder()
                .url(geocoderUrl)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code: " + response);
            GeocoderResponse result = gson.fromJson(
                    response.body().charStream(), GeocoderResponse.class);
            site = new Site(
                    result.getFormattedAddress(),
                    result.getLocation().getLat(),
                    result.getLocation().getLng());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(site);
    }
}

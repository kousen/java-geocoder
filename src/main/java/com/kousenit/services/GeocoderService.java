package com.kousenit.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kousenit.entities.Site;
import com.kousenit.exceptions.FunctionWithException;
import com.kousenit.json.GeocoderResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public class GeocoderService {
    private static final String BASE = "https://maps.googleapis.com/maps/api/geocode/json";
    private Gson gson;
    private OkHttpClient client = new OkHttpClient();

    public GeocoderService() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    String getEncodedAddress_usingTryCatch(String... address) {
        return Arrays.stream(address)
                .map(s -> {
                    try {
                        return URLEncoder.encode(s, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining(","));
    }

    private String encodeString(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    String getEncodedAddress_extractedMethod(String... address) {
        return Arrays.stream(address)
                .map(this::encodeString)
                .collect(Collectors.joining(","));
    }

    private <T, R, E extends Exception> Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    String getEncodedAddress_usingWrapper(String... address) {
        return Arrays.stream(address)
                .map(wrapper(s -> URLEncoder.encode(s, "UTF-8")))
                .collect(Collectors.joining(","));
    }

    public Optional<Site> getLatLng(String... address) {
        Site site = null;
        String geocoderUrl = String.format("%s?address=%s", BASE, getEncodedAddress_extractedMethod(address));
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

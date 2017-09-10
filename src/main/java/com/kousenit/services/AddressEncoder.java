package com.kousenit.services;

import com.kousenit.exceptions.FunctionWithException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddressEncoder {
    public String encodedAddressUsingTryCatch(String... address) {
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

    public String encodedAddressUsingExtractedMethod(String... address) {
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

    public String encodedAddressUsingWrapper(String... address) {
        return Arrays.stream(address)
                .map(wrapper(s -> URLEncoder.encode(s, "UTF-8")))
                .collect(Collectors.joining(","));
    }

}

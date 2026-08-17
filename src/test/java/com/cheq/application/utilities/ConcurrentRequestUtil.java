package com.cheq.application.utilities;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class ConcurrentRequestUtil {

    private ConcurrentRequestUtil() {
    }

    public static <T> CompletableFuture<T> executeAsync(
            Supplier<T> request) {

        return CompletableFuture.supplyAsync(request);
    }

    public static void waitForAll(
            CompletableFuture<?>... requests) {

        CompletableFuture.allOf(requests).join();
    }
}
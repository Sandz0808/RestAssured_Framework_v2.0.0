package com.cheq.contactlist.listeners;
import io.restassured.response.Response;


public final class RetryPolicy {

    private RetryPolicy() {
        // Prevent instantiation
    }

    // ==========================================================
    // NETWORK / INFRASTRUCTURE RETRY POLICY
    // ==========================================================
    /**
     * Determines whether an exception is eligible for retry.
     *
     * Retry only transient infrastructure failures.
     *
     * Retry:
     * - ConnectException
     * - SocketException
     * - SocketTimeoutException
     * - UnknownHostException
     * - NoHttpResponseException
     * - HttpHostConnectException
     * - SSLException
     * - SSLHandshakeException
     * - ConnectionClosedException
     * - EOFException
     *
     * No Retry:
     * - AssertionError
     * - NullPointerException
     * - IllegalArgumentException
     * - JsonParseException
     * - FileNotFoundException
     */
    public static boolean shouldRetry(Throwable throwable) {

        if (throwable == null) {
            return false;
        }

        Throwable cause = throwable;

        while (cause != null) {

            switch (cause.getClass().getSimpleName()) {

                case "ConnectException":
                case "SocketException":
                case "SocketTimeoutException":
                case "UnknownHostException":
                case "NoHttpResponseException":
                case "HttpHostConnectException":
                case "SSLException":
                case "SSLHandshakeException":
                case "ConnectionClosedException":
                case "EOFException":
                    return true;
            }

            cause = cause.getCause();
        }

        return false;
    }

    // ==========================================================
    // HTTP STATUS CODE RETRY POLICY
    // ==========================================================
    /**
     * Determines whether an HTTP response is eligible for retry.
     *
     * Usage:
     * if (RetryPolicy.shouldRetry(response)) {
     *      ...
     * }
     */
    public static boolean shouldRetry(Response response) {

        if (response == null) {
            return true;
        }

        return shouldRetry(response.getStatusCode());
    }

    // ==========================================================
    // HTTP STATUS CODE MATRIX
    // ==========================================================
    /**
     * Determines whether an HTTP status code is retryable.
     *
     * Retry:
     * 408 - Request Timeout
     * 425 - Too Early
     * 429 - Too Many Requests
     * 500 - Internal Server Error
     * 502 - Bad Gateway
     * 503 - Service Unavailable
     * 504 - Gateway Timeout
     * 507 - Insufficient Storage
     *
     * No Retry:
     * 400 - Bad Request
     * 401 - Unauthorized
     * 403 - Forbidden
     * 404 - Not Found
     * 405 - Method Not Allowed
     * 409 - Conflict
     * 410 - Gone
     * 412 - Precondition Failed
     * 415 - Unsupported Media Type
     * 422 - Unprocessable Entity
     */
    public static boolean shouldRetry(int statusCode) {

        return switch (statusCode) {

            case 408,
                 425,
                 429,
                 500,
                 502,
                 503,
                 504,
                 507 -> true;

            default -> false;
        };
    }

}
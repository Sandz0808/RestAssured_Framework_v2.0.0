package com.cheq.application.constants.contactlistconstant;

public final class ContactListHeaderConstant {

    private ContactListHeaderConstant() {
        // Prevent instantiation
    }

    // ==========================================================
    // STANDARD HEADERS
    // ==========================================================

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_HEADER = "application/json; charset=utf-8";
    public static final String ACCEPT = "Accept";
    public static final String AUTHORIZATION = "Authorization";
    public static final String X_API_KEY = "x-api-key";
    public static final String BEARER = "Bearer ";



    // ==========================================================
    // COMMON SECURITY HEADERS
    // ==========================================================

    public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    public static final String CONTENT_SECURITY_POLICY =  "Content-Security-Policy";
    public static final String STRICT_TRANSPORT_SECURITY =  "Strict-Transport-Security";
    public static final String REFERRER_POLICY =  "Referrer-Policy";
    public static final String CACHE_CONTROL = "Cache-Control";

    // ==========================================================
    // ALLOWED RESPONSE TIME
    // ==========================================================

    public static final int ALLOWED_RESPONSE_TIME = 2000;

}
package com.cheq.application.constants.contactlistconstant;

public class EndpointConstant {

    // -- USER ---
    public static final String CREATE_USER = "/users";
    public static final String GET_USER = "/users/me";
    public static final String UPDATE_USER = "/users/me";
    public static final String LOGOUT_USER = "/users/logout";
    public static final String LOGIN_USER = "/users/login";
    public static final String DELETE_USER = "/users/me";

    // --- CONTACT ---
    public static final String ADD_CONTACT = "/contacts";
    public static final String GET_CONTACT_LIST = "/contacts";
    public static final String GET_CONTACT = "/contacts/{id}";
    public static final String UPDATE_CONTACT = "/contacts/{id}";
    public static final String PUT_CONTACT = "/contacts/{id}";
    public static final String DELETE_CONTACT = "/contacts/{id}";

}
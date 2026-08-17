package com.cheq.application.models.plaidmodel.linktokenmodel;

import java.util.List;

public class CreateLinkToken {

    private String client_id;
    private String secret;
    private String client_name;
    private List<String> country_codes;
    private String language;
    private LinkTokenUser user;
    private List<String> products;
    private List<String> additional_consented_products;
    private HostedLink hosted_link;

    public CreateLinkToken() {

    }

    // --- SETTER ---

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void setCountry_codes(List<String> country_codes) {
        this.country_codes = country_codes;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUser(LinkTokenUser user) {
        this.user = user;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public void setAdditional_consented_products(
            List<String> additional_consented_products) {

        this.additional_consented_products =
                additional_consented_products;
    }

    public void setHosted_link(HostedLink hosted_link) {
        this.hosted_link = hosted_link;
    }




    // --- GETTER ---

    public String getClient_id() {
        return client_id;
    }

    public String getSecret() {
        return secret;
    }

    public String getClient_name() {
        return client_name;
    }

    public List<String> getCountry_codes() {
        return country_codes;
    }

    public String getLanguage() {
        return language;
    }

    public LinkTokenUser getUser() {
        return user;
    }

    public List<String> getProducts() {
        return products;
    }

    public List<String> getAdditional_consented_products() {
        return additional_consented_products;
    }

    public HostedLink getHosted_link() {
        return hosted_link;
    }
}
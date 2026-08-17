package com.cheq.application.models.plaidmodel.authenticationmodel;

import java.util.List;

public class CreatePublicToken {

    private String client_id;
    private String secret;
    private String institution_id;
    private List<String> initial_products;
    private PublicTokenOptions options;

    public CreatePublicToken() {
    }

    // --- SETTER ---

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setInstitution_id(String institution_id) {
        this.institution_id = institution_id;
    }

    public void setInitial_products(List<String> initial_products) {
        this.initial_products = initial_products;
    }

    public void setOptions(PublicTokenOptions options) {
        this.options = options;
    }

    // --- GETTER ---

    public String getClient_id() {
        return client_id;
    }

    public String getSecret() {
        return secret;
    }

    public String getInstitution_id() {
        return institution_id;
    }

    public List<String> getInitial_products() {
        return initial_products;
    }

    public PublicTokenOptions getOptions() {
        return options;
    }
}
package com.cheq.application.models.plaidmodel.authenticationmodel;

public class CreateProcessorToken {

    private String client_id;
    private String secret;
    private String institution_id;
    private ProcessorTokenOptions options;

    public CreateProcessorToken() {
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(String institution_id) {
        this.institution_id = institution_id;
    }

    public ProcessorTokenOptions getOptions() {
        return options;
    }

    public void setOptions(ProcessorTokenOptions options) {
        this.options = options;
    }
}
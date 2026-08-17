package com.cheq.application.models.plaidmodel.authenticationmodel;

public class PublicTokenOptions {

    private String webhook;
    private String override_username;
    private String override_password;

    public PublicTokenOptions() {
    }

    // --- SETTER ---

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public void setOverride_username(String override_username) {
        this.override_username = override_username;
    }

    public void setOverride_password(String override_password) {
        this.override_password = override_password;
    }

    // --- GETTER ---

    public String getWebhook() {
        return webhook;
    }

    public String getOverride_username() {
        return override_username;
    }

    public String getOverride_password() {
        return override_password;
    }
}
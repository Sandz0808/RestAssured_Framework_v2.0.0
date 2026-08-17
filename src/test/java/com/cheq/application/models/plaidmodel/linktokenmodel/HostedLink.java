package com.cheq.application.models.plaidmodel.linktokenmodel;

public class HostedLink {

    private String completion_redirect_uri;
    private boolean is_mobile_app;

    public HostedLink() {

    }

    // --- SETTER ---

    public void setCompletion_redirect_uri(String completion_redirect_uri) {
        this.completion_redirect_uri = completion_redirect_uri;
    }

    public void setIs_mobile_app(boolean is_mobile_app) {
        this.is_mobile_app = is_mobile_app;
    }

    // --- GETTER ---

    public String getCompletion_redirect_uri() {
        return completion_redirect_uri;
    }

    public boolean isIs_mobile_app() {
        return is_mobile_app;
    }
}
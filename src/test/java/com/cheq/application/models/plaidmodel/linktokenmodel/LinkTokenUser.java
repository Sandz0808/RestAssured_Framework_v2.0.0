package com.cheq.application.models.plaidmodel.linktokenmodel;

public class LinkTokenUser {

    private String clientUserId;

    public LinkTokenUser() {

    }

    // --- SETTER ---

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    // --- GETTER ---

    public String getClientUserId() {
        return clientUserId;
    }
}
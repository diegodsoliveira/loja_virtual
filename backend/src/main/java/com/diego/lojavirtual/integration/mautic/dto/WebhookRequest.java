package com.diego.lojavirtual.integration.mautic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookRequest {

    @JsonProperty("mautic.form_on_submit")
    private MauticFormOnSubmit mauticFormOnSubmit;

    @JsonProperty("timestamp")
    private String timestamp;

    public MauticFormOnSubmit getMauticFormOnSubmit() {
        return mauticFormOnSubmit;
    }

    public void setMauticFormOnSubmit(MauticFormOnSubmit mauticFormOnSubmit) {
        this.mauticFormOnSubmit = mauticFormOnSubmit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

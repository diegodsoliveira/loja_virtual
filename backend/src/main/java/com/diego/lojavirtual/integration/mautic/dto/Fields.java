package com.diego.lojavirtual.integration.mautic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Fields {

    @JsonProperty("core")
    private Map<String, Field> core;

    @JsonProperty("social")
    private Map<String, Field> social;

    public Map<String, Field> getCore() {
        return core;
    }

    public void setCore(Map<String, Field> core) {
        this.core = core;
    }

    public Map<String, Field> getSocial() {
        return social;
    }

    public void setSocial(Map<String, Field> social) {
        this.social = social;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "core=" + core +
                ", social=" + social +
                '}';
    }
}

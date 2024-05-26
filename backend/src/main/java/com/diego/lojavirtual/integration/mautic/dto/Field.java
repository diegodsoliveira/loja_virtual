package com.diego.lojavirtual.integration.mautic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {

    @JsonProperty("id")
    private String id;

    @JsonProperty("label")
    private String label;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("type")
    private String type;

    @JsonProperty("group")
    private String group;

    @JsonProperty("value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", alias='" + alias + '\'' +
                ", type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

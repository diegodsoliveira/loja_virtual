package com.diego.lojavirtual.integration.mautic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lead {

    @JsonProperty("id")
    private int id;

    @JsonProperty("points")
    private int points;

    @JsonProperty("color")
    private Object color;

    @JsonProperty("fields")
    private Fields fields;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "id=" + id +
                ", points=" + points +
                ", color=" + color +
                ", fields=" + fields +
                '}';
    }
}

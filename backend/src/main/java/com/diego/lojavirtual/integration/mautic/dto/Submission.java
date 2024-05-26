package com.diego.lojavirtual.integration.mautic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Map;

public class Submission {

    @JsonProperty("id")
    private int id;

    @JsonProperty("ipAddress")
    private String[] ipAddress;

    @JsonProperty("form")
    private Form form;

    @JsonProperty("lead")
    private Lead lead;

    @JsonProperty("trackingId")
    private String trackingId;

    @JsonProperty("dateSubmitted")
    private String dateSubmitted;

    @JsonProperty("referer")
    private String referer;

    @JsonProperty("page")
    private Object page;

    @JsonProperty("results")
    private Map<String, String> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String[] ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", ipAddress=" + Arrays.toString(ipAddress) +
                ", form=" + form +
                ", lead=" + lead +
                ", trackingId='" + trackingId + '\'' +
                ", dateSubmitted='" + dateSubmitted + '\'' +
                ", referer='" + referer + '\'' +
                ", page=" + page +
                ", results=" + results +
                '}';
    }
}

package com.salehni.salehni.data.model;

public class MechanicNotificationModel {
    int id;
    String request_img;
    String client_name;
    String description;
    String clock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest_img() {
        return request_img;
    }

    public void setRequest_img(String request_img) {
        this.request_img = request_img;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }
}

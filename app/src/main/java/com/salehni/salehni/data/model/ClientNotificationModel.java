package com.salehni.salehni.data.model;

public class ClientNotificationModel {
    int id;
    String request_img;
    String request;
    String request_type;
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }
}

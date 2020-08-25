package com.salehni.salehni.data.model;

public class ClientNotificationModel {
    int id;
    String mechanic_name;
    String request_id;
    String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMechanic_name() {
        return mechanic_name;
    }

    public void setMechanic_name(String mechanic_name) {
        this.mechanic_name = mechanic_name;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

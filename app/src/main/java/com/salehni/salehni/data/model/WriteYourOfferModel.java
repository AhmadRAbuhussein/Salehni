package com.salehni.salehni.data.model;

public class WriteYourOfferModel {
    int mechanic_id;
    String request_id;
    String price;
    String note;
    String voice_note;

    public int getMechanic_id() {
        return mechanic_id;
    }

    public void setMechanic_id(int mechanic_id) {
        this.mechanic_id = mechanic_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVoice_note() {
        return voice_note;
    }

    public void setVoice_note(String voice_note) {
        this.voice_note = voice_note;
    }
}

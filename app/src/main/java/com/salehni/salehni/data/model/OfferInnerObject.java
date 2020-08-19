package com.salehni.salehni.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OfferInnerObject implements Parcelable {

    String note;
    String voice_note;
    ArrayList<ItemsInnerObject> itemsInnerObjects;

    public OfferInnerObject() {

    }

    protected OfferInnerObject(Parcel in) {
        note = in.readString();
        voice_note = in.readString();
    }

    public static final Creator<OfferInnerObject> CREATOR = new Creator<OfferInnerObject>() {
        @Override
        public OfferInnerObject createFromParcel(Parcel in) {
            return new OfferInnerObject(in);
        }

        @Override
        public OfferInnerObject[] newArray(int size) {
            return new OfferInnerObject[size];
        }
    };

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

    public ArrayList<ItemsInnerObject> getItemsInnerObjects() {
        return itemsInnerObjects;
    }

    public void setItemsInnerObjects(ArrayList<ItemsInnerObject> itemsInnerObjects) {
        this.itemsInnerObjects = itemsInnerObjects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(note);
        parcel.writeString(voice_note);
    }
}

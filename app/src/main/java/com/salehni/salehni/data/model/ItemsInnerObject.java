package com.salehni.salehni.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemsInnerObject implements Parcelable {
    int id;
    String price;
    String title_en;
    String title_ar;

    public ItemsInnerObject() {

    }

    protected ItemsInnerObject(Parcel in) {
        id = in.readInt();
        price = in.readString();
        title_en = in.readString();
        title_ar = in.readString();
    }

    public static final Creator<ItemsInnerObject> CREATOR = new Creator<ItemsInnerObject>() {
        @Override
        public ItemsInnerObject createFromParcel(Parcel in) {
            return new ItemsInnerObject(in);
        }

        @Override
        public ItemsInnerObject[] newArray(int size) {
            return new ItemsInnerObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getTitle_ar() {
        return title_ar;
    }

    public void setTitle_ar(String title_ar) {
        this.title_ar = title_ar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(price);
        parcel.writeString(title_en);
        parcel.writeString(title_ar);
    }
}

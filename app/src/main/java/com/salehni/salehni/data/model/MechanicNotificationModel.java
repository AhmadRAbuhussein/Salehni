package com.salehni.salehni.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MechanicNotificationModel implements Parcelable {
    int id;
    String customer_name;
    String request_id;
    String time;

    public MechanicNotificationModel() {

    }

    protected MechanicNotificationModel(Parcel in) {
        id = in.readInt();
        customer_name = in.readString();
        request_id = in.readString();
        time = in.readString();
    }

    public static final Creator<MechanicNotificationModel> CREATOR = new Creator<MechanicNotificationModel>() {
        @Override
        public MechanicNotificationModel createFromParcel(Parcel in) {
            return new MechanicNotificationModel(in);
        }

        @Override
        public MechanicNotificationModel[] newArray(int size) {
            return new MechanicNotificationModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(customer_name);
        parcel.writeString(request_id);
        parcel.writeString(time);
    }
}

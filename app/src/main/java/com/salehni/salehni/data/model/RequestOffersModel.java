package com.salehni.salehni.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.salehni.salehni.viewmodel.RequestOffersViewModel;

import java.util.ArrayList;

public class RequestOffersModel implements Parcelable {
    int id;
    int provider_id;
    int request_id;
    String provider_name;
    String total_price;
    String working_days;
    OfferInnerObject offerInnerObject;

    public RequestOffersModel() {

    }

    public RequestOffersModel(Parcel in) {
        id = in.readInt();
        provider_id = in.readInt();
        request_id = in.readInt();
        provider_name = in.readString();
        total_price = in.readString();
        working_days = in.readString();
    }

    public static final Creator<RequestOffersModel> CREATOR = new Creator<RequestOffersModel>() {
        @Override
        public RequestOffersModel createFromParcel(Parcel in) {
            return new RequestOffersModel(in);
        }

        @Override
        public RequestOffersModel[] newArray(int size) {
            return new RequestOffersModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getWorking_days() {
        return working_days;
    }

    public void setWorking_days(String working_days) {
        this.working_days = working_days;
    }

    public OfferInnerObject getOfferInnerObject() {
        return offerInnerObject;
    }

    public void setOfferInnerObject(OfferInnerObject offerInnerObject) {
        this.offerInnerObject = offerInnerObject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(provider_id);
        parcel.writeInt(request_id);
        parcel.writeString(provider_name);
        parcel.writeString(total_price);
        parcel.writeString(working_days);
    }
}

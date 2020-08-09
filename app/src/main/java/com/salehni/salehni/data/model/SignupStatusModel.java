package com.salehni.salehni.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SignupStatusModel implements Parcelable {

    boolean status;
    String otp;

    public SignupStatusModel() {

    }

    protected SignupStatusModel(Parcel in) {
        status = in.readByte() != 0;
        otp = in.readString();
    }

    public static final Creator<SignupStatusModel> CREATOR = new Creator<SignupStatusModel>() {
        @Override
        public SignupStatusModel createFromParcel(Parcel in) {
            return new SignupStatusModel(in);
        }

        @Override
        public SignupStatusModel[] newArray(int size) {
            return new SignupStatusModel[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status ? 1 : 0));
        parcel.writeString(otp);
    }
}

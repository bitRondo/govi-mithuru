package com.example.govimithuruapp.claimManagement;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Claim implements Parcelable {
    private int year;
    private String claimID, landName, landRegNum;
    private float landArea, cultArea, damageArea;
    private ArrayList<Evidence> evidences;

    public Claim (String claimID) {
        this.claimID = claimID;
        evidences = new ArrayList<>();
    }

    protected Claim(Parcel in) {
        year = in.readInt();
        claimID = in.readString();
        landName = in.readString();
        landRegNum = in.readString();
        landArea = in.readFloat();
        cultArea = in.readFloat();
        damageArea = in.readFloat();
        evidences = in.readArrayList(Evidence.class.getClassLoader());
    }

    public static final Creator<Claim> CREATOR = new Creator<Claim>() {
        @Override
        public Claim createFromParcel(Parcel in) {
            return new Claim(in);
        }

        @Override
        public Claim[] newArray(int size) {
            return new Claim[size];
        }
    };

    public String getClaimID() {
        return claimID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getLandRegNum() {
        return landRegNum;
    }

    public void setLandRegNum(String landRegNum) {
        this.landRegNum = landRegNum;
    }

    public float getLandArea() {
        return landArea;
    }

    public void setLandArea(float landArea) {
        this.landArea = landArea;
    }

    public float getCultArea() {
        return cultArea;
    }

    public void setCultArea(float cultArea) {
        this.cultArea = cultArea;
    }

    public float getDamageArea() {
        return damageArea;
    }

    public void setDamageArea(float damageArea) {
        this.damageArea = damageArea;
    }

    public void addEvidence(Evidence evidence) {
        evidences.add(evidence);
    }

    public Evidence getEvidence(int index) {
        return evidences.get(index);
    }

    public void removeEvidence(Evidence evidence) {
        evidences.remove(evidence);
    }

    public int getNumOfEvidences() {
        return evidences.size() - 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeString(claimID); dest.writeString(landName); dest.writeString(landRegNum);
        dest.writeFloat(landArea); dest.writeFloat(cultArea); dest.writeFloat(damageArea);
        dest.writeList(evidences);
    }
}

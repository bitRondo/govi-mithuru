package com.example.govimithuruapp.claimManagement;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Claim implements Parcelable, Serializable {
    private String claimID, agriServiceCenter, gramaNiladhariDiv, farmerRegNo,
        farmerName, farmerAddress, farmerPhone, farmerNIC, landRegNum, landName,
        crop, damageCause, damageLevel, bankAccountNo, bank, branch;
    private float landArea, cultivatedArea, timeToHarvest, damageArea, compensationAmount;
    private Date cultivatedDate, damageDate;
    private ArrayList<Evidence> evidences;

    public Claim (String claimID) {
        this.claimID = claimID;
        evidences = new ArrayList<>();
    }

    protected Claim(Parcel in) {
        claimID = in.readString();
        agriServiceCenter = in.readString();
        farmerRegNo = in.readString();
        farmerName = in.readString();
        farmerPhone = in.readString();
        farmerNIC = in.readString();
        landRegNum = in.readString();
        landArea = in.readFloat();
        crop = in.readString();
        cultivatedArea = in.readFloat();
        damageDate = new Date(in.readLong());
        damageCause = in.readString();
        damageLevel = in.readString();
        damageArea = in.readFloat();
        compensationAmount = in.readFloat();
        bankAccountNo = in.readString();
        bank = in.readString();
        branch = in.readString();

        gramaNiladhariDiv = in.readString();
        farmerAddress = in.readString();
        landName = in.readString();
        timeToHarvest = in.readFloat();

        evidences = in.readArrayList(Evidence.class.getClassLoader());
        if (in.dataAvail() > 0) cultivatedDate = new Date(in.readLong());
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

    public String getAgriServiceCenter() {
        return agriServiceCenter;
    }

    public void setAgriServiceCenter(String agriServiceCenter) {
        this.agriServiceCenter = agriServiceCenter;
    }

    public String getGramaNiladhariDiv() {
        return gramaNiladhariDiv;
    }

    public void setGramaNiladhariDiv(String gramaNiladhariDiv) {
        this.gramaNiladhariDiv = gramaNiladhariDiv;
    }

    public String getFarmerRegNo() {
        return farmerRegNo;
    }

    public void setFarmerRegNo(String farmerRegNo) {
        this.farmerRegNo = farmerRegNo;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerPhone() {
        return farmerPhone;
    }

    public void setFarmerPhone(String farmerPhone) {
        this.farmerPhone = farmerPhone;
    }

    public String getFarmerNIC() {
        return farmerNIC;
    }

    public void setFarmerNIC(String farmerNIC) {
        this.farmerNIC = farmerNIC;
    }

    public String getLandRegNum() {
        return landRegNum;
    }

    public void setLandRegNum(String landRegNum) {
        this.landRegNum = landRegNum;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getDamageCause() {
        return damageCause;
    }

    public void setDamageCause(String damageCause) {
        this.damageCause = damageCause;
    }

    public String getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(String damageLevel) {
        this.damageLevel = damageLevel;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public float getLandArea() {
        return landArea;
    }

    public void setLandArea(float landArea) {
        this.landArea = landArea;
    }

    public float getCultivatedArea() {
        return cultivatedArea;
    }

    public void setCultivatedArea(float cultivatedArea) {
        this.cultivatedArea = cultivatedArea;
    }

    public float getTimeToHarvest() {
        return timeToHarvest;
    }

    public void setTimeToHarvest(float timeToHarvest) {
        this.timeToHarvest = timeToHarvest;
    }

    public float getDamageArea() {
        return damageArea;
    }

    public void setDamageArea(float damageArea) {
        this.damageArea = damageArea;
    }

    public float getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(float compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public Date getCultivatedDate() {
        return cultivatedDate;
    }

    public void setCultivatedDate(Date cultivatedDate) {
        this.cultivatedDate = cultivatedDate;
    }

    public Date getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(Date damageDate) {
        this.damageDate = damageDate;
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
        dest.writeString(claimID);
        dest.writeString(agriServiceCenter);
        dest.writeString(farmerRegNo);
        dest.writeString(farmerName);
        dest.writeString(farmerPhone);
        dest.writeString(farmerNIC);
        dest.writeString(landRegNum);
        dest.writeFloat(landArea);
        dest.writeString(crop);
        dest.writeFloat(cultivatedArea);
        dest.writeLong(damageDate.getTime());
        dest.writeString(damageCause);
        dest.writeString(damageLevel);
        dest.writeFloat(damageArea);
        dest.writeFloat(compensationAmount);
        dest.writeString(bankAccountNo);
        dest.writeString(bank);
        dest.writeString(branch);

        dest.writeString(gramaNiladhariDiv);
        dest.writeString(farmerAddress);
        dest.writeString(landName);
        dest.writeFloat(timeToHarvest);

        dest.writeList(evidences);
        if (cultivatedDate != null) dest.writeLong(cultivatedDate.getTime());
    }
}

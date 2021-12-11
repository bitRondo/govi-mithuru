package com.example.govimithuruapp.claimManagement;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.govimithuruapp.core.BackendManager;
import com.example.govimithuruapp.core.UtilityManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

enum State {
    SUBMISSION_PENDING(0), SUBMITTED(1);

    private int value;

    public int getValue() {
        return this.value;
    }

    State (int value) {
        this.value = value;
    }
}

public class Claim implements Parcelable, Serializable {
    private String claimID, topic, agriServiceCenter, gramaNiladhariDiv, farmerRegNo,
        farmerName, farmerAddress, farmerPhone, farmerNIC, landRegNum, landName,
        crop, bankAccountNo, bank, branch, otherCause, acceptedFields, damageLevelComment, compensationComment;
    private int state, damageCause, damageLevel;
    private float landArea, cultivatedArea, timeToHarvest, damageArea, compensationAmount;
    private Date cultivatedDate, damageDate;
    private ArrayList<Evidence> evidences;

    public Claim (String claimID) {
        this.claimID = claimID;
        state = State.SUBMISSION_PENDING.getValue();
        otherCause = ""; acceptedFields = ""; damageLevelComment = ""; compensationComment = "";
        evidences = new ArrayList<>();
    }

    protected Claim(Parcel in) {
        claimID = in.readString();
        state = in.readInt();
        topic = in.readString();
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
        damageCause = in.readInt();
        damageLevel = in.readInt();
        damageArea = in.readFloat();
        compensationAmount = in.readFloat();
        bankAccountNo = in.readString();
        bank = in.readString();
        branch = in.readString();

        gramaNiladhariDiv = in.readString();
        farmerAddress = in.readString();
        landName = in.readString();
        timeToHarvest = in.readFloat();

        otherCause = in.readString();
        acceptedFields = in.readString();
        damageLevelComment = in.readString();
        compensationComment = in.readString();

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

    public String getTopic() { return topic; }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getDamageCause() {
        return damageCause;
    }

    public void setDamageCause(int damageCause) {
        this.damageCause = damageCause;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
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

    public ArrayList<Evidence> getAllEvidences() {
        return this.evidences;
    }

    public Evidence getEvidence(int index) {
        return evidences.get(index);
    }

    public void removeEvidence(int index) {
        evidences.remove(index);
    }

    public int getNumOfEvidences() {
        return evidences.size() - 1;
    }

    public String getOtherCause() {
        return otherCause;
    }

    public void setOtherCause(String otherCause) {
        this.otherCause = otherCause;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(claimID);
        dest.writeInt(state);
        dest.writeString(topic);
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
        dest.writeInt(damageCause);
        dest.writeInt(damageLevel);
        dest.writeFloat(damageArea);
        dest.writeFloat(compensationAmount);
        dest.writeString(bankAccountNo);
        dest.writeString(bank);
        dest.writeString(branch);

        dest.writeString(gramaNiladhariDiv);
        dest.writeString(farmerAddress);
        dest.writeString(landName);
        dest.writeFloat(timeToHarvest);

        dest.writeString(otherCause);
        dest.writeString(acceptedFields);
        dest.writeString(damageLevelComment);
        dest.writeString(compensationComment);

        dest.writeList(evidences);
        if (cultivatedDate != null) dest.writeLong(cultivatedDate.getTime());
    }

    public void postClaimToBackend(Context context) {
        HashMap<String, String> data = new HashMap<>();
        data.put("claimID", claimID);
        data.put("state", String.valueOf(state));
        data.put("topic", topic);
        data.put("agriServiceCenter", agriServiceCenter);
        data.put("farmerRegNo", farmerRegNo);
        data.put("farmerName", farmerName);
        data.put("farmerPhone", farmerPhone);
        data.put("farmerNIC", farmerNIC);
        data.put("landRegNum", landRegNum);
        data.put("landArea", String.valueOf(landArea));
        data.put("crop", crop);
        data.put("cultivatedArea", String.valueOf(cultivatedArea));
        data.put("damageDate", UtilityManager.getInstance().formatDate(damageDate));
        data.put("damageCause", String.valueOf(damageCause));
        data.put("damageLevel", String.valueOf(damageLevel));
        data.put("damageArea", String.valueOf(damageArea));
        data.put("compensation", String.valueOf(compensationAmount));
        data.put("bankAccountNo", bankAccountNo);
        data.put("bank", bank);
        data.put("branch", branch);

        data.put("gramaNiladhariDiv", gramaNiladhariDiv);
        data.put("address", farmerAddress);
        data.put("otherCause", otherCause);

        data.put("evidenceCount", String.valueOf(evidences.size()));

        if (timeToHarvest > 0) data.put("timeToHarvest", String.valueOf(timeToHarvest));
        if (cultivatedDate != null) data.put("cultivatedDate",
                UtilityManager.getInstance().formatDate(cultivatedDate));

        BackendManager.getInstance(context).postTextData(BackendManager.CLAIM_SUFFIX, data, BackendManager.ActionCodes.SUBMIT_CLAIM);
    }
}

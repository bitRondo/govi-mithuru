package com.example.govimithuruapp.claimManagement;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Evidence implements Parcelable {
    private String evidenceID;
    private Date date;
    private Location location;
    private String description;
    private String photoPath;

    public Evidence(String evidenceID, Date date, Location location, String photoPath) {
        this.evidenceID = evidenceID;
        this.date = date;
        this.location = location;
        description = "";
        this.photoPath = photoPath;
    }

    protected Evidence(Parcel in) {
        evidenceID = in.readString();
        date = new Date(in.readLong());
        location = in.readParcelable(Location.class.getClassLoader());
        description = in.readString();
        photoPath = in.readString();
    }

    public static final Creator<Evidence> CREATOR = new Creator<Evidence>() {
        @Override
        public Evidence createFromParcel(Parcel in) {
            return new Evidence(in);
        }

        @Override
        public Evidence[] newArray(int size) {
            return new Evidence[size];
        }
    };

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvidenceID() { return evidenceID; }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoPath() { return photoPath; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(evidenceID);
        dest.writeLong(date.getTime());
        dest.writeParcelable(location, flags);
        dest.writeString(description);
        dest.writeString(photoPath);
    }
}

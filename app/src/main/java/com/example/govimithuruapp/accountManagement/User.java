package com.example.govimithuruapp.accountManagement;

import java.io.Serializable;

public class User implements Serializable {
    private char userType;
    private String agriServiceCenter;
    private String gramaNiladhariDiv;
    private String regNo;
    private String name, address, phone, nic;

    private char preferredLocale;

    public User(char userType, char preferredLocale) {
        this.userType = userType;
        this.preferredLocale = preferredLocale;
    }

    public User(char userType, String agriServiceCenter, String gramaNiladhariDiv, String regNo,
                String name, String address, String phone, String nic, char preferredLocale) {
        this.userType = userType;
        this.agriServiceCenter = agriServiceCenter;
        this.gramaNiladhariDiv = gramaNiladhariDiv;
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.nic = nic;
        this.preferredLocale = preferredLocale;
    }

    public char getUserType() {
        return userType;
    }

    public String getAgriServiceCenter() {
        return agriServiceCenter;
    }

    public String getGramaNiladhariDiv() {
        return gramaNiladhariDiv;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getNIC() {
        return nic;
    }

    public char getPreferredLocale() {
        return preferredLocale;
    }
}

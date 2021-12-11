package com.example.govimithuruapp.accountManagement;

import com.example.govimithuruapp.claimManagement.Claim;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
    private char userType;
    private String agriServiceCenter;
    private String gramaNiladhariDiv;
    private String regNo;
    private String name, address, phone, nic;

    private boolean isAuthenticated;

    private char preferredLocale;

    private HashMap<String, Claim> claims;

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
        this.claims = new HashMap<>();
    }

    public User(char userType, String agriServiceCenter, String gramaNiladhariDiv, String regNo,
                String name, String address, String phone, String nic, char preferredLocale, HashMap<String, Claim> claims) {
        this.userType = userType;
        this.agriServiceCenter = agriServiceCenter;
        this.gramaNiladhariDiv = gramaNiladhariDiv;
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.nic = nic;
        this.preferredLocale = preferredLocale;
        this.claims = claims;
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

    public HashMap<String, Claim> getAllClaims() {
        return claims;
    }

    public int getNumOfClaims() {
        return this.claims.size();
    }

    public void addClaim(String claimID, Claim claim) {
        this.claims.put(claimID, claim);
    }

    public Claim getClaim(String claimID) {
        return this.claims.get(claimID);
    }

    public void updateClaim(String claimID, Claim claim) {
        this.claims.put(claimID, claim);
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    // Mapping from server-side UserType (1:f, 2:a)
    public static char mapUserType(int type) {
        if (type == 1) return 'f';
        return 'a';
    }

    // Mapping from server-side PreferredLocale ("SI":'s', "EN", 'e')
    public static char mapPreferredLocale(String locale) {
        if (locale.equals("SI")) return 's';
        return 'e';
    }
}

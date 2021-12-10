package com.example.govimithuruapp.claimManagement;

import android.content.Context;

import com.example.govimithuruapp.core.UtilityManager;

public class EvidenceManager {

    public static final int PAD_SIZE = 4;
    public static final String PAD_CHAR = "0";
    public static final int SUBMIT_EVIDENCE = 200;

    // Singleton
    private static EvidenceManager instance;

    private EvidenceManager() { }

    public static EvidenceManager getInstance() {
        if (instance == null) instance = new EvidenceManager();
        return instance;
    }

    public String generateEvidenceID(String claimID, int counter) {
        return String.format("%s_%s", claimID, UtilityManager.getInstance().padNumber(counter+1, PAD_SIZE, PAD_CHAR));
    }

    public void uploadEvidences(Claim claim, Context context) {
        for (Evidence e : claim.getAllEvidences()) e.postEvidenceToBackend(context);
    }
}

package com.example.govimithuruapp.claimManagement;

import android.content.Context;

import com.example.govimithuruapp.accountManagement.AuthController;
import com.example.govimithuruapp.core.UtilityManager;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class ClaimManager {
    public static final int PAD_SIZE = 4;
    public static final String PAD_CHAR = "0";

    private ArrayList<Claim> submissionQueue;
    private Claim currentClaim;

    // Singleton
    private static ClaimManager instance;

    private ClaimManager() {
        submissionQueue = new ArrayList<>();
    }

    public static ClaimManager getInstance() {
        if (instance == null) instance = new ClaimManager();
        return instance;
    }

    public Claim createNewClaim() {
        String claimID = UtilityManager.getInstance().padNumber(
                AuthController.getInstance().getCurrentUser().getNumOfClaims() + 1, PAD_SIZE, PAD_CHAR);
        claimID = String.format("%s_%s", AuthController.getInstance().getCurrentUser().getRegNo(), claimID);
        return new Claim(claimID);
    }

    public Claim getCurrentClaim() {
        return currentClaim;
    }

    public void setCurrentClaim(Claim currentClaim) {
        this.currentClaim = currentClaim;
    }

    public void submitClaim(Claim claim, Context context) {
        submissionQueue.add(claim);
        System.out.println("Need to send");
        claim.postClaimToBackend(context);
        EvidenceManager.getInstance().uploadEvidences(claim, context);
    }

    public void saveClaimInUser(Context context) {
        Claim claim = null;
        if (!submissionQueue.isEmpty()) claim = submissionQueue.remove(0);
        if (claim != null) {
            claim.setState(State.SUBMITTED.getValue());
            AuthController.getInstance().getCurrentUser().addClaim(claim.getClaimID(), claim);
            AuthController.getInstance().saveUser(context);
        }
    }
}

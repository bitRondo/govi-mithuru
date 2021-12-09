package com.example.govimithuruapp.claimManagement;

import android.content.Context;

import com.example.govimithuruapp.accountManagement.AuthController;
import com.example.govimithuruapp.core.UtilityManager;

import java.util.PriorityQueue;

public class ClaimManager {
    public static final int PAD_SIZE = 4;
    public static final String PAD_CHAR = "0";
    public static final int SUBMIT_CLAIM = 100;

    private PriorityQueue<Claim> submissionQueue;

    // Singleton
    private static ClaimManager instance;

    private ClaimManager() {
        submissionQueue = new PriorityQueue<>();
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

    public String generateEvidenceID(String claimID, int counter) {
        return String.format("%s_%s", claimID, UtilityManager.getInstance().padNumber(counter, PAD_SIZE, PAD_CHAR));
    }

    public void submitClaim(Claim claim, Context context) {
        submissionQueue.add(claim);
        System.out.println("Need to send");
        claim.postClaimToBackend(context);
    }

    public void saveClaimInUser() {
        Claim claim = submissionQueue.poll();
        if (claim != null) {
            claim.setState(State.SUBMITTED.getValue());
            AuthController.getInstance().getCurrentUser().addClaim(claim.getClaimID(), claim);
        }
    }
}

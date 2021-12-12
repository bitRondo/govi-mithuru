package com.example.govimithuruapp.accountManagement;

import android.content.Context;

import com.example.govimithuruapp.core.BackendManager;
import com.example.govimithuruapp.core.LocaleManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AuthController {
    private static User currentUser;
    private static final String USER_DATA_FILE = "userdata.ser";

    private static final User anonymousUser = new User('u', 'e');
    private static User possibleCurrentUser;

    public static final String USER_URL = "users/";

    // Singleton
    private static AuthController instance;

    private AuthController() {
    }

    public static AuthController getInstance() {
        if (instance == null) instance = new AuthController();
        return instance;
    }

    public boolean saveUser(Context context) {
        try {
            System.out.println("Saving User");
            // Save in the path returned by getFilesDir()
            FileOutputStream fos = context.openFileOutput(USER_DATA_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currentUser);
            oos.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getPossibleCurrentUser() { return possibleCurrentUser; }

    public User getSavedUser(Context context) {
        try {
            FileInputStream fis = context.openFileInput(USER_DATA_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            currentUser = (User) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            currentUser = anonymousUser;
            currentUser.setAuthenticated(false);
            saveUser(context);
        }
        return currentUser;
    }

    public void completeLoginStep1(JSONObject o, Context context, boolean success) {
        if (success) {
            try {
                possibleCurrentUser = new User(
                        User.mapUserType(Integer.parseInt(o.getString("userType"))),
                        o.getString("agriServiceCenter"),
                        o.getString("gramaNiladhariDiv"),
                        o.getString("regNo"),
                        o.getString("name"),
                        o.getString("address"),
                        o.getString("phone"),
                        o.getString("nic"),
                        User.mapPreferredLocale(o.getString("preferredLocale")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Login1Activity activity = (Login1Activity) context;
        activity.setResultOfLoginStep1(success);
    }

    public void loginStep1(Context context, String nic) {
        possibleCurrentUser = getSavedUser(context);
        System.out.println(possibleCurrentUser.getUserType());
        // Case 1: Logging in from locally available user data (userType == 'f' or 'a')
        if (possibleCurrentUser.getUserType() != 'u' && possibleCurrentUser.getNIC().equals(nic)) {
            Login1Activity activity = (Login1Activity) context;
            activity.setResultOfLoginStep1(true);
        }
        // Case 2: Logging in from remote server
        else {
            System.out.println("Need to login from remote server");
            BackendManager.getInstance(context).getData(String.format("%s%s", USER_URL, nic), BackendManager.ActionCodes.LOGIN_STEP_1);
        }
    }

    public boolean loginStep2(String regNo) {
        return regNo.equals(possibleCurrentUser.getRegNo());
    }

    public void authenticateUser(Context context) {
        currentUser = possibleCurrentUser;
        currentUser.setAuthenticated(true);
        saveUser(context);
    }

    public void logoutUser(Context context) {
        currentUser.setAuthenticated(false);
        saveUser(context);
    }

}

package com.example.govimithuruapp.accountManagement;

import android.content.Context;

import com.example.govimithuruapp.core.LocaleManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AuthController {
    private static User currentUser;
    private static final String USER_DATA_FILE = "userdata.ser";

    // Singleton
    private static AuthController instance;

    private AuthController() { }

    public static AuthController getInstance() {
        if (instance == null) instance = new AuthController();
        return instance;
    }

    public boolean saveUser(Context context) {
        currentUser = new User('f', "a01", "g01", "f001", "Disura", "Panadura",
                "0761234567", "2021V", 's');

        try {
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

    public boolean getSavedUser(Context context) {
        try {
            FileInputStream fis = context.openFileInput(USER_DATA_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            currentUser = (User) ois.readObject();
            ois.close();
            fis.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginStep1 (Context context, String nic) {
        if (currentUser == null) getSavedUser(context);
        // Case 1: Logging in from locally available user data (userType == 'f' or 'a')
        if (currentUser.getUserType() != 'u') {
            return nic.equals(currentUser.getNIC());
        }
        // Case 2: Logging in from remote server
        else {
            System.out.println("Need to login from remote server");
        }
        return false;
    }

    public boolean loginStep2 (String regNo) {
        boolean success = regNo.equals(currentUser.getRegNo());
        if (currentUser.getUserType() != 'u') {
            if (success) {
                if (currentUser.getPreferredLocale() == 's') {
                    LocaleManager.setAppLocaleSinhala();
                } else {
                    LocaleManager.setAppLocaleEnglish();
                }
            }
            return success;
        } else {
            System.out.println("Need to login from remote server");
        }
        return false;
    }

}

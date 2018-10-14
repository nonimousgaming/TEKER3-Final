package com.example.lenovo.te_ker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    public static final String PREF_USER_LOGIN = "isLoggedIn";

    public static final String PREF_USER_ID = "ID";

    public static boolean getLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isLoggedIn = sp.getBoolean(PREF_USER_LOGIN, false);

        return isLoggedIn;
    }

    public static void setLogin(Context context, boolean status) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_USER_LOGIN, status);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sp.getString(PREF_USER_ID, "");

        return ID;
    }

    public static void setUserId(Context context, String user_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_USER_ID, user_id);
        editor.apply();
    }
}

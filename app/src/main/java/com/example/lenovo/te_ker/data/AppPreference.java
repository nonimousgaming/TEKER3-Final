package com.example.lenovo.te_ker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    public static final String PREF_USER_LOGIN = "isLoggedIn";

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
}

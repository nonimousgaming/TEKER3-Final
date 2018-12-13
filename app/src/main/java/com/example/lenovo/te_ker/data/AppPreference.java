package com.example.lenovo.te_ker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    public static final String PREF_USER_LOGIN = "isLoggedIn";

    public static final String PREF_USER_ID = "ID";
    public static final String PREF_EMAIL = "EMAIL";
    public static final String PREF_NAME = "NAME";
    public static final String PREF_SECTION_ID = "ID";
    public static final String PREF_SECTION_NAME = "NAME";

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

    public static String getEmail(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String Email = sp.getString(PREF_EMAIL, "");

        return Email;
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    public static String getName(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String Name = sp.getString(PREF_NAME, "");

        return Name;
    }

    public static void setName(Context context, String name) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_NAME, name);
        editor.apply();
    }

    public static String getPrefSectionId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sp.getString(PREF_SECTION_ID, "");

        return ID;
    }

    public static void setPrefSectionId(Context context, String section_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_SECTION_ID, section_id);
        editor.apply();
    }

    public static String getPrefSectionName(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sp.getString(PREF_SECTION_NAME, "");

        return ID;
    }

    public static void setPrefSectionName(Context context, String section_name) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_SECTION_NAME, section_name);
        editor.apply();
    }


}

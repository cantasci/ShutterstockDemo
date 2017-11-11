package com.cantasci.shutterstock.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class Prefs {
    private static final String PREFS_NAME = "ShutterPrefs";

    public static final String PREF_USER_ID               = "user_id";
    public static final String PREF_TOKEN                 = "token";
    public static final String PREF_LOGIN                 = "login";
    public static final String PREF_PASSWORD              = "password";


    public static int getInt(Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(key, -1);
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, null);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public static String getToken(Context context) {
        return getString(context, PREF_TOKEN);
    }

    public static int getUserId(Context context) {
        return getInt(context, PREF_USER_ID);
    }

    public static void put(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public static String getLogin(Context context) {
        return getString(context, PREF_LOGIN);
    }

    public static void saveUserLogin(Context context, String login, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_LOGIN, login);
        editor.putString(PREF_PASSWORD, password);
        editor.commit();
    }

    public static void createInstanceIfNeeded(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.commit();
    }
    public static String getPassword(Context context) {
        return getString(context, PREF_PASSWORD);
    }


    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }




}

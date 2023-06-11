package com.example.placarbasquete.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtil {
    private SharedPreferences preference;

    public SharedPreferenceUtil(Context context){
        preference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setAcessConfig(String field, String value) {
        preference.edit().putString(field, value).commit();
    }

    public String getAccess(String field) {
        String access = preference.getString(field,"");
        return access;
    }

}

package com.dyas.bookingevent.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dyas on 3/7/17.
 */

public class SessionManager {


    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    public static final String language ="language";




    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public static void setUserObject(Context c, String userObject, String key) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, userObject);
        editor.commit();
    }

    public static String getUserObject(Context ctx, String key) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String userObject = pref.getString(key, null);
        return userObject;
    }



}

package com.example.wocure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;


public final class SavedPref {
    public  static SharedPreferences sharedPreferences;
    public static SavedPref savedPref;

    private SavedPref (Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences ( context );
    }

    public static boolean setData(String key, String value){
        return sharedPreferences.edit ().putString ( key, value).commit ();
    }
    public String getData(String key) {
        if (!sharedPreferences.contains(key)) {
            return "";
        } else {
            String profileImage = this.sharedPreferences.getString(key, "");
            return profileImage;
        }
    }
    public static SavedPref getSavedPreferenceInstance(){
        if(null == savedPref){
            savedPref = new SavedPref (ApplicationClass.getAppInstance());
        }
        return savedPref;
    }


}

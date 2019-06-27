package com.example.pokedex.Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    SharedPreferences sharedPreferences;

    // Constructor
    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    // Getter and Setter
    public void setSearch(String search) {
        sharedPreferences.edit().putString("search", search).commit();
    }

    public String getSearch() {
        return sharedPreferences.getString("search", "ditto");
    }
}

package Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManagerV3 {
    private static volatile SharedPreferencesManagerV3 instance = null;
    private static final String DB_FILE = "DB_FILE";
    private SharedPreferences sharedPref;

    private SharedPreferencesManagerV3(Context context) {
        this.sharedPref = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        synchronized (SharedPreferencesManagerV3.class) {
            if (instance == null) {
                instance = new SharedPreferencesManagerV3(context);
            }
        }
    }

    public static SharedPreferencesManagerV3 getInstance() {
        return instance;
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPref.getInt(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPref.getString(key, defaultValue);
    }
}

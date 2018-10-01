package tob.leis.randomshot.helper;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper for reading and writing preference information
 */

public class PreferenceHelper {

    private static final String PREFERENCES = "Preferences";

    private static final String USERNAME_PREFERENCE = "usernamePreference";
    private static final String AGE_PREFERENCE = "agePreference";
    private static final String HEIGHT_PREFERENCE = "heightPreference";
    private static final String EMAIL_PREFERENCE = "emailPreference";
    private static final String DEBUG_ENABLE_PREFERENCE = "debugEnablePreference";
    private static final String SAMPLING_PREFERENCE = "samplingPreference";
    private static final String ACCEL_RANGE_PREFERENCE = "accelRangePreference";

    //public static final String ACCEL_ENABLED_PREFERENCE = "accelEnabledPreference";


    /**
     * Writes a string value with a given key to the shared preferences.
     *
     * @param username string value that is going to be saved
     */
    public static void setUsernamePreference(Context context, String username) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USERNAME_PREFERENCE, username);
        editor.apply();
    }

    /**
     * Reads a string with a given key from the shared preferences.
     * @param context context of the app
     * @return mode from preferences
     */
    public static String getUsernamePreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(USERNAME_PREFERENCE, "ID000");
    }



    public static void setAgePreference(Context context, String age) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AGE_PREFERENCE, age);
        editor.apply();
    }
    public static String getAgePreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(AGE_PREFERENCE, "21");
    }



    public static void setHeightPreference(Context context, String height) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(HEIGHT_PREFERENCE, height);
        editor.apply();
    }
    public static String getHeightPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(HEIGHT_PREFERENCE, "180");
    }



    public static void setEmailPreference(Context context, String email) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EMAIL_PREFERENCE, email);
        editor.apply();
    }
    public static String getEmailPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(EMAIL_PREFERENCE, "tobiasleis8@gmail.com");
    }



    public static void setDebuggingPreference(Context context, boolean debuggingEnabled) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(DEBUG_ENABLE_PREFERENCE, debuggingEnabled);
        editor.apply();
    }
    public static boolean getDebuggingPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(DEBUG_ENABLE_PREFERENCE, true);
    }



    public static void setSamplingRatePreference(Context context, float range) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(SAMPLING_PREFERENCE, range);
        editor.apply();
    }
    public static float getSamplingRatePreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getFloat(SAMPLING_PREFERENCE, 51.2f);
    }



    public static void setAccelRangePreference(Context context, int range) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ACCEL_RANGE_PREFERENCE, range);
        editor.apply();
    }
    public static int getAccelPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getInt(ACCEL_RANGE_PREFERENCE, 4);
    }
}

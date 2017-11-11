package com.cantasci.shutterstock.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class CommonUtils {
    /***
     * Get the device screen width
     *
     * @param context
     * @return int
     */
    public static int getScreenWidth(@NonNull Context context) {
        Point size = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    /***
     * Get the device screen height
     *
     * @param context
     * @return int
     */
    public static int getScreenHeight(@NonNull Context context) {
        Point size = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

    /***
     * Convert dip to pixel
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }


    /**
     *
     * @param error
     * @return
     */
    public static String parseRetrofitError(RetrofitError error) {
        if (error.getResponse() != null && error.getResponse().getBody() != null) {
            try {
                String se = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                JsonObject errorJson = new JsonParser().parse(se).getAsJsonObject();
                return formatRetrofitError(errorJson);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return error.getMessage();
    }

    private static String formatRetrofitError(JsonObject error) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, JsonElement> entry : error.entrySet()) {
            JsonElement jsonElement = entry.getValue();
            if (!jsonElement.isJsonNull()) {
                sb.append(jsonElement.getAsString());
                sb.append("\n");
            }
        }
        // remove \n in the end
        return sb.toString().substring(0, sb.length() - 1);
    }

    /***
     * Check whether the orientation is in landscape or portrait
     *
     * @param context
     * @return boolean
     */
    public static boolean isInLandscapeMode(@NonNull Context context) {
        boolean isLandscape = false;
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
        }
        return isLandscape;
    }
}

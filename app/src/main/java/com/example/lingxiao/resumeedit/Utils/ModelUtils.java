package com.example.lingxiao.resumeedit.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lingxiao.resumeedit.model.Education;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by lingxiao on 3/22/18.
 */

public class ModelUtils {

    private static String PREF_NAME = "model";
    private static Gson gson = new Gson();

    public static void save(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //format object to gson string
        String gsonToString = gson.toJson(object);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, gsonToString);
        editor.apply();

    }

    public static <T> T read(Context context, String key, TypeToken<T> typeToken) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String value = sp.getString(key, "");
        T objectFromJson = gson.fromJson(value, typeToken.getType());
        return objectFromJson;
    }
}

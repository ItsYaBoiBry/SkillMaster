package com.example.damianlzy.skillmaster.functions;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.damianlzy.skillmaster.model.User;

public class Sessions {
    SharedPreferences preferences;
    Context context;
    String preference_key = "SharedPreferences";
    String token_key = "token";

    public Sessions(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(preference_key, Context.MODE_PRIVATE);
    }

    public void StoreToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(token_key, token);
        editor.apply();
    }

    public void StoreUser(int id
            , String name
            , String email
            , String created_at
            , String updated_at
            , String mobile
            , String address
            , String nric
            , String dob
            , String gender
            , String nationality
            , String education_level
            , String race
            , String occupation
            , String salary) {
        SharedPreferences.Editor editor = preferences.edit();
        User user = new User();
        editor.putInt(user.user_id_key, id);
        editor.putString(user.user_name_key, name);
        editor.putString(user.user_email_key, email);
        editor.putString(user.user_created_at_key, created_at);
        editor.putString(user.user_updated_at_key, updated_at);
        editor.putString(user.user_mobile_key, mobile);
        editor.putString(user.user_address_key, address);
        editor.putString(user.user_nric_key, nric);
        editor.putString(user.user_dob_key, dob);
        editor.putString(user.user_gender_key, gender);
        editor.putString(user.user_nationality_key, nationality);
        editor.putString(user.user_education_level_key, education_level);
        editor.putString(user.user_race_key, race);
        editor.putString(user.user_occupation_key, occupation);
        editor.putString(user.user_salary_key, salary);
        editor.apply();
    }

    public User GetUser() {
        User user = new User();
        return new User(preferences.getInt(user.user_id_key, -1)
                , preferences.getString(user.user_name_key, "")
                , preferences.getString(user.user_email_key, "")
                , preferences.getString(user.user_created_at_key, "")
                , preferences.getString(user.user_updated_at_key, "")
                , preferences.getString(user.user_mobile_key, "")
                , preferences.getString(user.user_address_key, "")
                , preferences.getString(user.user_nric_key, "")
                , preferences.getString(user.user_dob_key, "")
                , preferences.getString(user.user_gender_key, "")
                , preferences.getString(user.user_nationality_key, "")
                , preferences.getString(user.user_education_level_key, "")
                , preferences.getString(user.user_race_key, "")
                , preferences.getString(user.user_occupation_key, "")
                , preferences.getString(user.user_salary_key, "")
        );
    }
}

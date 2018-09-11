package com.example.damianlzy.skillmaster.model;

import java.io.Serializable;

public class User implements Serializable{
    public String user_id_key = "id";
    public String user_name_key = "name";
    public String user_email_key = "email";
    public String user_created_at_key = "created_at";
    public String user_updated_at_key = "updated_at";
    public String user_mobile_key = "mobile";
    public String user_address_key = "address";
    public String user_nric_key = "nric";
    public String user_dob_key = "dob";
    public String user_gender_key = "gender";
    public String user_nationality_key = "nationality";
    public String user_education_level_key = "education_level";
    public String user_race_key = "race";
    public String user_occupation_key = "occupation";
    public String user_salary_key = "salary";

    public String user_password_key = "password";
    public String user_cmf_password_key = "c_password";
    private int user_id;
    private String user_name, user_email, user_created_at, user_updated_at, user_mobile, user_address, user_nric, user_dob, user_gender, user_nationality, user_education_level, user_race, user_occupation, user_salary;
    public User(){

    }

    public User(int user_id, String user_name, String user_email, String user_created_at, String user_updated_at, String user_mobile, String user_address, String user_nric, String user_dob, String user_gender, String user_nationality, String user_education_level, String user_race, String user_occupation, String user_salary) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_created_at = user_created_at;
        this.user_updated_at = user_updated_at;
        this.user_mobile = user_mobile;
        this.user_address = user_address;
        this.user_nric = user_nric;
        this.user_dob = user_dob;
        this.user_gender = user_gender;
        this.user_nationality = user_nationality;
        this.user_education_level = user_education_level;
        this.user_race = user_race;
        this.user_occupation = user_occupation;
        this.user_salary = user_salary;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_created_at() {
        return user_created_at;
    }

    public String getUser_updated_at() {
        return user_updated_at;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public String getUser_address() {
        return user_address;
    }

    public String getUser_nric() {
        return user_nric;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_nationality() {
        return user_nationality;
    }

    public String getUser_education_level() {
        return user_education_level;
    }

    public String getUser_race() {
        return user_race;
    }

    public String getUser_occupation() {
        return user_occupation;
    }

    public String getUser_salary() {
        return user_salary;
    }
}

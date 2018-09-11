package com.example.damianlzy.skillmaster.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class CourseDetails implements Serializable {
    public String cd_id_key = "id";
    public String cd_name_key = "name";
    public String cd_category_key = "category";
    public String cd_instructor_key = "instructor";
    public String cd_description_key = "description";
    public String cd_course_fee_key = "course_fee";
    public String cd_address_key = "address";
    public String cd_prod_picture_key = "prod_picture";
    public String cd_status_key = "status";
    public String cd_created_at_key = "created_at";
    public String cd_updated_at_key = "updated_at";
    public String cd_schedule_key = "schedule";

    private int id;
    private String name;
    private String category;
    private String instructor;
    private String description;
    private String course_fee;
    private String address;
    private String prod_picture;
    private String status;
    private JSONObject created_at;
    private JSONObject updated_at;
    private JSONArray schedule;
    public CourseDetails(){

    }

    public CourseDetails(int id, String name, String category, String instructor, String description, String course_fee, String address, String prod_picture, String status, JSONObject created_at, JSONObject updated_at, JSONArray schedule) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.instructor = instructor;
        this.description = description;
        this.course_fee = course_fee;
        this.address = address;
        this.prod_picture = prod_picture;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public String getCourse_fee() {
        return course_fee;
    }

    public String getAddress() {
        return address;
    }

    public String getProd_picture() {
        return prod_picture;
    }

    public String getStatus() {
        return status;
    }

    public JSONObject getCreated_at() {
        return created_at;
    }

    public JSONObject getUpdated_at() {
        return updated_at;
    }

    public JSONArray getSchedule() {
        return schedule;
    }
}

package com.example.damianlzy.skillmaster.model;

import java.io.Serializable;

public class Courses implements Serializable {
    public String course_id_key = "id";
    public String course_category_id_key = "category_id";
    public String course_name_key = "name";
    public String course_instructor_key = "instructor";
    public String course_course_fee_key = "course_fee";
    public String course_description_key = "description";
    public String course_address_key = "address";
    public String course_prod_picture_key = "prod_picture";
    public String course_status_key = "id";
    public String course_created_at_key = "id";
    public String course_updated_at_key = "id";

    private int course_id;
    private int course_category_id;
    private String course_name;
    private String course_instructor;
    private String course_course_fee;
    private String course_description;
    private String course_address;
    private String course_prod_picture;
    private String course_status;
    private String course_created_at;
    private String course_updated_at;

    public Courses(int course_id, int course_category_id, String course_name, String course_instructor, String course_course_fee, String course_description, String course_address, String course_prod_picture, String course_status, String course_created_at, String course_updated_at) {
        this.course_id = course_id;
        this.course_category_id = course_category_id;
        this.course_name = course_name;
        this.course_instructor = course_instructor;
        this.course_course_fee = course_course_fee;
        this.course_description = course_description;
        this.course_address = course_address;
        this.course_prod_picture = course_prod_picture;
        this.course_status = course_status;
        this.course_created_at = course_created_at;
        this.course_updated_at = course_updated_at;
    }
    public Courses(){

    }

    public int getCourse_id() {
        return course_id;
    }

    public int getCourse_category_id() {
        return course_category_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_instructor() {
        return course_instructor;
    }

    public String getCourse_course_fee() {
        return course_course_fee;
    }

    public String getCourse_description() {
        return course_description;
    }

    public String getCourse_address() {
        return course_address;
    }

    public String getCourse_prod_picture() {
        return course_prod_picture;
    }

    public String getCourse_status() {
        return course_status;
    }

    public String getCourse_created_at() {
        return course_created_at;
    }

    public String getCourse_updated_at() {
        return course_updated_at;
    }
}

package com.example.damianlzy.skillmaster.model;

public class Links {
    public Links() {

    }

    private String hostlink = "http://www.ehostingcentre.com/skillmaster";
    private String publiclink = "/public";
    private String apilink = "/api";

    public String CoursesLink() {
        return hostlink + publiclink + apilink + "/courses";
    }

    public String CourseDetailsLink(int id) {
        return hostlink + publiclink + apilink + "/courseDetails/"+String.valueOf(id);
    }

    public String loginLink() {
        return hostlink + publiclink + apilink + "/login";
    }

    public String registerLink() {
        return hostlink + publiclink + apilink + "/register";
    }
    public String registerCourseLink(){return hostlink+publiclink+apilink+"/courseRegistration";}

}

package com.example.myapplication.model;

public class Admin extends Student {
    private String group;


    public String getGroup() {
        return group;
    }

    public Admin(String firstName, String lastName, String userName, String level, String group) {

        this.group = group;
    }
    public Admin(String userName, String group) {
        this.group = group;
        this.userName = userName;
    }
	
	public Admin(String firstName, String lastName, String userName, String level, String profilePic, String group){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.level = level;
        this.ProfilePic = profilePic;
    }

}

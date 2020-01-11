package com.example.myapplication.model;

import java.util.ArrayList;

public class Teacher extends User {

   private String teacherId ;
   private ArrayList<String> levels ;

    public Teacher() {
    }
	public Teacher(String firstName, String lastName, String userName, ArrayList<String> levels, String profilePic){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.ProfilePic = profilePic;
        this.levels =  levels;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public ArrayList<String> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<String> levels) {
        this.levels = levels;
    }

    @Override
    public void partagerInformaion() {

    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", levels=" + levels +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +


                '}';
    }
}

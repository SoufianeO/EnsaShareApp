package com.example.myapplication.model;

import com.example.myapplication.DAO.InvitationDao;

import java.util.HashMap;

public class Student extends User  {

   private String studentId ;
   protected String level ;

   private HashMap<String,Object> groups ;

   public Student(String firstName, String lastName, String userName, String level, String profilePic){
       this.firstName = firstName;
       this.lastName = lastName;
       this.userName = userName;
       this.ProfilePic = profilePic;
       this.level = level;
    }
	public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public HashMap<String, Object> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Object> groups) {
        this.groups = groups;
    }

    public Student() {
    }

    public Student(String firstName, String lastName, String userName, String level) {
        super();
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void partagerInformaion() {

    }


    @Override
    public String toString() {
        return "Student{" +
                "level='" + level + '\'' +
                ", groups=" + groups.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", ProfilePic='" + ProfilePic + '\'' +
                '}';
    }

    public void sendInvitation(Invitation invitation){

        InvitationDao inv = new InvitationDao();

        inv.stockInvitation(invitation);

    }
}


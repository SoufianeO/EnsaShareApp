package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {
    private String name;
    private List<Student> listStudents;

    public List<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(List<Student> listStudents) {
        this.listStudents = listStudents;
    }

    public List<Teacher> getListTeacher() {
        return listTeacher;
    }

    public void setListTeacher(List<Teacher> listTeacher) {
        this.listTeacher = listTeacher;
    }

    private List<Teacher> listTeacher;
    private Admin admin;
    private String groupId;
    private String urlImage;

    public Group(){

    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +

                ", admin=" + admin.getUserName() +
                ", groupId='" + groupId + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}

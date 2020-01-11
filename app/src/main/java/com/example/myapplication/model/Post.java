package com.example.myapplication.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private Student student;
    private String urlFile;
    private String typeFile;
    private String content;
    private String date;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    private String idPost;

    public Post(Student student, String urlFile, String typeFile, String content){
        this.student = student;
        this.urlFile = urlFile;
        this.typeFile = typeFile;
        this.content = content;
    }

    public Post() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getIdPost() {
        return idPost;
    }


}

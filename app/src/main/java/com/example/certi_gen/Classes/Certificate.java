package com.example.certi_gen.Classes;

import android.graphics.Bitmap;

public class Certificate {
    public String imageRef;
    public String name;
    public String emailId;
    public String size;
    public String position;
    public String phoneNumber;
    public String rollNo;

    public String getFolderRelation() {
        return folderRelation;
    }

    public void setFolderRelation(String folderRelation) {
        this.folderRelation = folderRelation;
    }

    public String folderRelation;

    public Certificate(String imageRef, String name, String emailId, String size, String position, String phoneNumber, String rollNo) {
        this.imageRef = imageRef;
        this.name = name;
        this.emailId = emailId;
        this.size = size;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.rollNo = rollNo;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public Certificate() {
    }
}


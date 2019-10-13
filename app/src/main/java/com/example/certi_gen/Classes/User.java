package com.example.certi_gen.Classes;

public class User {
    public String name;
    public String emailid;
    public String imageRef;

    public User() {
    }

    public User(String name, String emailid, String imageRef) {
        this.name = name;
        this.emailid = emailid;
        this.imageRef = imageRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }
}

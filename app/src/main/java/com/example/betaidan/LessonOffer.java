package com.example.betaidan;

public class LessonOffer {
    private String Name, Phone, SClass, Date, Price, Subject, uid,About,Experience;

    public LessonOffer(){}
    public LessonOffer(String Name, String Phone, String Date, String Price, String Subject, String uid,String About,String Experience) {
        this.Name = Name;
        this.Phone = Phone;
        this.Date = Date;
        this.Price = Price;
        this.Subject = Subject;
        this.uid = uid;
        this.About=About;
        this.Experience=Experience;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }
}
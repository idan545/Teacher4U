package com.example.betaidan;

public class LessonOffer {
    private String Name, Phone, SClass, Date, Price, Subject, uid;


    public LessonOffer(String Name, String Phone, String SClass, String Date, String Price, String Subject, String uid) {
        this.Name = Name;
        this.SClass = SClass;
        this.Phone = Phone;
        this.Date = Date;
        this.Price = Price;
        this.Subject = Subject;
        this.uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSClass() {
        return SClass;
    }

    public void setSClass(String SClass) {
        this.SClass = SClass;
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
}
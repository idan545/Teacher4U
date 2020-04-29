package com.example.betaidan;

public class LessonOffer {
    /**
     * @author		Idan Cohen
     * @version	    V1.0
     * @since		10/4/2020
     * Constructor for offering a private lesson.
     */
    private String Name, Phone, Date, Price, Subject, uid,About,Experience,uidteach;
    private boolean Act;
    long count;

    public LessonOffer(){}
    public LessonOffer(String Name, String Phone, String Date, String Price, String Subject, String uid,String About,
                       String Experience,Boolean Act,Long count,String uidteach) {
        this.Name = Name;
        this.Phone = Phone;
        this.Date = Date;
        this.Price = Price;
        this.uidteach=uidteach;
        this.Subject = Subject;
        this.uid = uid;
        this.About=About;
        this.count=count;
        this.Act=Act;
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

    public boolean isAct() {
        return Act;
    }

    public void setAct(boolean act) {
        Act = act;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getUidteach() {
        return uidteach;
    }

    public void setUidteach(String uidteach) {
        this.uidteach = uidteach;
    }
}
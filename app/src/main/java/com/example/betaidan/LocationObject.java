package com.example.betaidan;

import android.location.Location;

public class LocationObject {
    private String myLocation,Subject,Date,uid,name,Phone,SClass;
    private int status;
    private boolean Act;

    public  LocationObject(){}
    public LocationObject (String myLocation,String Subject,String Date, String uid,String name,String Phone,String SClass,int status,Boolean Act){
        this.Subject=Subject;
        this.myLocation=myLocation;
        this.Date=Date;
        this.Act=Act;
        this.status=status;
        this.uid=uid;
        this.name=name;
        this.SClass=SClass;
        this.Phone=Phone;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }
    public void setPhone(String phone) {
        this.Phone=phone;
    }
    public String getPhone() {
        return Phone;
    }

    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

   public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSClass() {
        return SClass;
    }

    public void setSClass(String SClass) {
        this.SClass = SClass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

    }

    public boolean isAct() {
        return Act;
    }

    public void setAct(boolean act) {
        Act = act;
    }
}

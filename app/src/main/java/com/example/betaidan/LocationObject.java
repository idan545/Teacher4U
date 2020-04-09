package com.example.betaidan;

import android.location.Location;

public class LocationObject {
    private String myLocation,Subject,Date,uid;

    private  LocationObject(){}
    public LocationObject (String myLocation,String Subject,String Date, String uid){
        this.Subject=Subject;
        this.myLocation=myLocation;
        this.Date=Date;

        //   this.uid=uid;
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


    /*public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

  /*  public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }*/
}

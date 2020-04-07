package com.example.betaidan;

import android.location.Location;

public class LocationObject {
    private String myLocation,Subject,uid;

    private  LocationObject(){}
    public LocationObject (String myLocation,String Subject,String uid){
        this.Subject=Subject;
        this.myLocation=myLocation;

        //   this.uid=uid;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
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

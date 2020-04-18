package com.example.betaidan;

import android.location.Location;

public class LocationObject {
  private String myLocation,Subject,Date,uid,name,Phone,SClass,uidteach = "",price = "";
  private int status;
  private boolean Act;
  private long count;

  public  LocationObject(){}
  public LocationObject (String myLocation,String Subject,String Date, String uid,String name,String Phone,String SClass,
                         int status,Boolean Act,long count,String uidteach,String price){
    this.Subject=Subject;
    this.myLocation=myLocation;
    this.Date=Date;
    this.Act=Act;
    this.count=count;
    this.uidteach=uidteach;
    this.status=status;
    this.price=price;
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

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
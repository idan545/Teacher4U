package com.example.betaidan;

public class Teacher {
  /**
   * @author		Idan Cohen
   * @version	    V1.0
   * @since		8/4/2020
   * Constructor for teacher class.
   */
  private String name,phone,Experience,About,uid,email;
  private  Boolean yes;


  public Teacher (){}
  public Teacher (String FullName,String Phone,String email,String Experience,String About,String uid, Boolean yes) {
    this.name=FullName;
    this.phone=Phone;
    this.yes = yes;
    this.email=email;
    this.Experience=Experience;
    this.uid=uid;
    this.About=About;

  }
  public String getName() {
    return name;
  }

  public void setName(String FullName) {
    this.name=FullName;
  }
  public void setPhone(String phone) {
    this.phone=phone;
  }
  public String getPhone() {
    return phone;
  }
  public String getUid() {
    return uid;
  }
  public void setUid(String uid) {
    this.uid=uid;
  }
  public void setAbout(String About){
    this.About=About;
  }
  public String getAbout(){
    return About;
  }
  public void setExperience(String Experience){
    this.Experience=Experience;
  }
  public String getExperience(){
    return Experience;
  }
  public Boolean getYes() {
    return yes;
  }

  public void setYes(Boolean yes) {
    this.yes = yes;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
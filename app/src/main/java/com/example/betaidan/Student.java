package com.example.betaidan;

public class Student {
  /**
   * @author		Idan Cohen
   * @version	    V1.0
   * @since		7/4/2020
   * Constructor for student class.
   */
  private String name, SClass,Phone,uid,email;



  public Student(){}
  public Student (String FullName, String SClass,String Phone,String uid, String email) {
    this.name=FullName;
    this.SClass=SClass;
    this.Phone=Phone;
    this.email = email;
    this.uid=uid;
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
  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid=uid;
  }

  public String getSClass() {
    return SClass;
  }

  public void setSClass(String SClass) {
    this.SClass = SClass;
  }


  /*public void copyStudent(Student student){
      this.name = student.getName();
      this.SClass = student.getStudentClass();
      this.Phone = student.getPhone();
      this.uid = student.getUid();
  }*/
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

package model;


public class People {

  private long id;
  private String name;
  private String sex;
  private long age;
  private String title;
  private String politicalstatus;
  private String highestdegree;
  private java.sql.Timestamp termtime;
  private java.sql.Timestamp arrivetime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getPoliticalstatus() {
    return politicalstatus;
  }

  public void setPoliticalstatus(String politicalstatus) {
    this.politicalstatus = politicalstatus;
  }


  public String getHighestdegree() {
    return highestdegree;
  }

  public void setHighestdegree(String highestdegree) {
    this.highestdegree = highestdegree;
  }


  public java.sql.Timestamp getTermtime() {
    return termtime;
  }

  public void setTermtime(java.sql.Timestamp termtime) {
    this.termtime = termtime;
  }


  public java.sql.Timestamp getArrivetime() {
    return arrivetime;
  }

  public void setArrivetime(java.sql.Timestamp arrivetime) {
    this.arrivetime = arrivetime;
  }

}

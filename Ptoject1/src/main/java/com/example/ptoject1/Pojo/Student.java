package com.example.ptoject1.Pojo;

public class Student {
    private Integer sid;
    private String sname;
    private Integer classid;
    private String password;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public void setClass(Integer sclass) {
        this.classid = sclass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student() {
    }

    public Student(Integer sid, String sname, Integer classid, String password) {
        this.sid = sid;
        this.sname = sname;
        this.classid = classid;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", classid='" + classid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}



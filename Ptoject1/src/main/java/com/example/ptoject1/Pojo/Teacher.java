package com.example.ptoject1.Pojo;

public class Teacher {
    private Integer tid;
    private String tname;
    private String password;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Teacher() {
    }

    public Teacher(Integer tid, String tname, String password) {
        this.tid = tid;
        this.tname = tname;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

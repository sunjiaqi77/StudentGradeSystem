package com.example.ptoject1.Pojo;


public class Course {
    private Integer cid;
    private String cname;
    private Integer tid;
    private String tname;
    private float ccredit;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public float getCcredit() {
        return ccredit;
    }

    public void setCcredit(float ccredit) {
        this.ccredit = ccredit;
    }

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

    public Course() {
    }

    public Course(Integer cid, String cname, Integer tid, String tname, float ccredit) {
        this.cid = cid;
        this.cname = cname;
        this.tid = tid;
        this.tname = tname;
        this.ccredit = ccredit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", tid=" + tid +
                ", tname='" + tname + '\'' +
                ", ccredit=" + ccredit +
                '}';
    }
}

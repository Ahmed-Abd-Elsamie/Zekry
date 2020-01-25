package com.zekry.model;


public class Zekr {

     private int id;
     private String zekr;
     private int num;

    public Zekr() {
    }

    public Zekr(int id, String zekr, int num) {
        this.id = id;
        this.zekr = zekr;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZekr() {
        return zekr;
    }

    public void setZekr(String zekr) {
        this.zekr = zekr;
    }


}
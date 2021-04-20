package com.example.bajiesleep.entity;

public class TestBean {
    public String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "sn='" + sn + '\'' +
                '}';
    }
}

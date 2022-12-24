package com.example.demock.Model;

public class LichHen {
    private String time,phone;

    public LichHen() {
    }

    public LichHen(String time, String phone) {
        this.time = time;
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

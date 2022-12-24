package com.example.demock.Admin;

import java.util.HashMap;
import java.util.Map;

public class LichHen {

    private int id;
    private String ten;
    private String lichHen;

    public LichHen() {
    }

    public LichHen(int id, String ten, String lichHen) {
        this.id = id;
        this.ten = ten;
        this.lichHen = lichHen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLichHen() {
        return lichHen;
    }

    public void setLichHen(String lichHen) {
        this.lichHen = lichHen;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten", ten);
        result.put("lichHen", lichHen);

        return result;
    }
}

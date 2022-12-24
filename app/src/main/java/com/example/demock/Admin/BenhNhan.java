package com.example.demock.Admin;

import java.util.HashMap;
import java.util.Map;

public class BenhNhan {

    private int id;
    private String ten;
    private String sdt;

    public BenhNhan() {
    }

    public BenhNhan(int id, String ten, String sdt) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten", ten);
        result.put("sdt", sdt);

        return result;
    }
}

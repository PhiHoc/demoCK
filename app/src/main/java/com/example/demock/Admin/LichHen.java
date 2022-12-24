package com.example.demock.Admin;

import java.util.HashMap;
import java.util.Map;

public class LichHen {

    private String sdt;
    private String ten;
    private String lichHen;

    public LichHen() {
    }

    public LichHen(String sdt, String ten, String lichHen) {
        this.sdt = sdt;
        this.ten = ten;
        this.lichHen = lichHen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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

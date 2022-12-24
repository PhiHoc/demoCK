package com.example.demock.Admin;

import java.util.HashMap;
import java.util.Map;

public class VatTu {

    private int id;
    private String ten;
    private int soLuong;
    private String hanSuDung;

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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public VatTu() {
    }

    public VatTu(int id, String ten, int soLuong, String hanSuDung) {
        this.id = id;
        this.ten = ten;
        this.soLuong = soLuong;
        this.hanSuDung = hanSuDung;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten", ten);
        result.put("soLuong", soLuong);
        result.put("hanSuDung", hanSuDung);

        return result;
    }
}

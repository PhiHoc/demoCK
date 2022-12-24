package com.example.demock.Common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Utilities {
    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatDateTime(String datetime){
        String[] dt = datetime.split(" ");
        String[] dmy = dt[0].split("/");
        return dmy[0] + "-" + dmy[1] +"-"+ dmy[2] + " " + dt[1];
    }
}

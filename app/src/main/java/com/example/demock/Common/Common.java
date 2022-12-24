package com.example.demock.Common;

import com.example.demock.Model.User;

public class Common {
    public static User currentUser;
    public static String currentPhone;
    public static void clear(){
        currentUser =  null;
    }
}

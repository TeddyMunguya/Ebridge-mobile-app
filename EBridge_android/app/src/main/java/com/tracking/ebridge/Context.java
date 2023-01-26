package com.tracking.ebridge;

public class Context {

    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Context.username = username;
    }

    public static String getUrl = "http://192.168.100.112/ebridge/";
}

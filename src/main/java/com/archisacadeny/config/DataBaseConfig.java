package com.archisacadeny.config;

public class DataBaseConfig {
    // access modifiers -> public, protected, default, private
    // username, password, database name, url
    public static final String DATABASE_NAME = "ums";
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "12345";
    public static final String URL = "jdbc:postgresql://localhost:5432/"+DATABASE_NAME;

}

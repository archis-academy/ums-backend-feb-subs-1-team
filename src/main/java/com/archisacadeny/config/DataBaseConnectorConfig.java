package com.archisacadeny.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectorConfig {

    private static Connection connection;

    public static void setConnection(){
        try{
            connection = DriverManager.getConnection(DataBaseConfig.URL,DataBaseConfig.USER_NAME,DataBaseConfig.PASSWORD);
            System.out.println("Connected Successfully...");
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void closeConnection(){
        if(connection !=null){
            try{
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

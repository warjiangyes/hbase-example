package org.example.phoenix.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhoenixTest {
    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        Connection connection = DriverManager.getConnection("jdbc:phoenix:192.168.128.178:2181");
        PreparedStatement statement = connection.prepareStatement("select * from PERSON");


        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("NAME"));
        }
        statement.close();
        connection.close();
    }
}

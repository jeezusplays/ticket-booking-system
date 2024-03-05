package com.myapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/ticket-booking-system?serverTimezone=Asia/Singapore";
        String user = "root";
        String password = "";
        executeSqlScript("/sql/create_tables.sql", url, user, password);
        // executeSqlScript("/sql/insert_data.sql", url, user, password);
    }


    private static void executeSqlScript(String resourcePath, String url, String user, String password) {
        // Load SQL from file
        try (InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            StringBuilder sql = new StringBuilder();
            reader.lines().forEach(line -> sql.append(line).append("\n"));

            // Use ";" as a delimiter to execute multiple statements
            String[] sqlStatements = sql.toString().split(";");
            for (String statement : sqlStatements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement);
                }
            }
            System.out.println("Successfully executed script: " + resourcePath);

        } catch (Exception e) {
            System.err.println("Failed to execute script: " + resourcePath);
            e.printStackTrace();
        }
    }
}

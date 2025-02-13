package kz.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Action extends SqlDatas{
    private final String getQuery = "SELECT status, username, action, \"time\" FROM public.notification;";
    private final String insert = "INSERT INTO public.notification(status, username, action, \"time\") VALUES (?, ?, ?, ?)";

    public Action() {
    }

    public void addAction(String action){
        Scanner scan = new Scanner(action);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd 'and' HH:mm:ss"));

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insert)) {

            pstmt.setString(1, scan.next());
            pstmt.setString(2, scan.next());
            pstmt.setString(3, scan.next());
            pstmt.setString(4, now);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewActions(User user){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getQuery)) {

            while (rs.next()) {
                String status = rs.getString("status");
                String username = rs.getString("username");
                String action = rs.getString("action");
                String time = rs.getString("time");
                System.out.println("Status: " + status + ", Username: " + username + ", Action: " + action + ", Time: " + time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

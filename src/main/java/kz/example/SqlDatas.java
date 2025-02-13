package kz.example;

import java.sql.*;

public class SqlDatas {
    protected static final String URL = "URL_OF_YOUR_SQL_DATABASE";
    protected static final String USER = "YOUR_LOGIN";
    protected static final String PASSWORD = "YOUR_PASSWORD";
    static String bookQuery = "SELECT id FROM public.books ORDER BY id DESC limit 1;";
    static String userQuery = "SELECT id FROM public.users ORDER BY id DESC limit 1;";
    static String adminQuery = "SELECT id FROM public.admins ORDER BY id DESC limit 1;";
    public static int bookId = 1;
    public static int userId = 1;
    public static int adminId = 1;

    static {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st1 = conn.createStatement();
             Statement st2 = conn.createStatement();
             Statement st3 = conn.createStatement();
             ResultSet rs1 = st1.executeQuery(bookQuery);
             ResultSet rs2 = st2.executeQuery(userQuery);
             ResultSet rs3 = st3.executeQuery(adminQuery)) {

            // Проверяем, есть ли данные перед вызовом getInt()
            if (rs1.next()) bookId = rs1.getInt("id") + 1;
            if (rs2.next()) userId = rs2.getInt("id") + 1;
            if (rs3.next()) adminId = rs3.getInt("id") + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

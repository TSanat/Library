package kz.example;
import java.sql.*;
import java.util.*;

public class User extends SqlDatas{
    private int id;
    private String username;
    private String password;
    private final String getQuery = "SELECT id, username, password, status, borrowedbooks FROM public.users";
    private final String insert = "UPDATE users SET borrowedbooks = array_append(borrowedbooks, ?) WHERE id = ?";
    private final String delete = "UPDATE users SET borrowedbooks = array_remove(borrowedbooks, ?) WHERE id = ?";

    private int status;
    //Status == 1 is Admin
    //Status == 2 is User

    public User(int id, String name, String password, int status) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.status = status;
    }
    public void addBook(Book book){
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(insert)){
            pstmt.setInt(1, book.getId());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void removeBook(Book book){
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(delete)){
            pstmt.setInt(1, book.getId());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public ArrayList<Long> getBorrowedBooks() {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(getQuery);){

            while (rs.next()) {
                int Id = rs.getInt("id");
                if (Id == id) {
                    Array sqlArray = rs.getArray("borrowedbooks");
                    if (sqlArray != null) {
                        Long[] borrowedBook = (Long[]) sqlArray.getArray();

                        ArrayList<Long> bookList = new ArrayList<>(Arrays.asList(borrowedBook));

                        return bookList;
                    }
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}

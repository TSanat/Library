package kz.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Library extends SqlDatas{
    private final Action action;
    private final LibraryControlUsers libraryControlStock;
    private final LibraryControlBooks libraryControlBooks;
    private final ControlViewOptions cv;
    private final String getQuery = "SELECT id, title, author, year, quantity FROM public.books";



    public Library(Action action) {
        this.action = action;
        cv = new ControlViewOptions();
        libraryControlStock = new LibraryControlUsers(action);
        libraryControlBooks = new LibraryControlBooks(action);
    }

    public void addAdmin(User user) throws InterruptedException { libraryControlStock.addAdmin(user); }
    public void removeAdmin(User user) throws InterruptedException { libraryControlStock.removeAdmin(user); }
    public boolean addUser(User user) throws InterruptedException { return libraryControlStock.addUser(user); }
    public boolean removeUser(User user) throws InterruptedException { return libraryControlStock.removeUser(user, new Library(action)); }
    public void getInfo(User user){ cv.viewInfo(user);}


    public void addBook(Book book, User user){ libraryControlBooks.addBook(book, user); }
    public void removeBook(int id, User user) throws InterruptedException { libraryControlBooks.removeBook(getBookById(id), user); }
    public void borrowBook(Book book, User user) throws InterruptedException { libraryControlBooks.borrowBook(book, user); }
    public void returnBook(Book book, User user) throws InterruptedException { libraryControlBooks.returnBook(book, user); }
    public void myBorrowedBooks(User user){ libraryControlBooks.myBorrowedBooks(user); }
    public void viewAllBooks(User user){ libraryControlBooks.viewAllBooks(user); }
    public void viewAvailableBooks(User user){ libraryControlBooks.viewAvailableBooks(user); }
    public void viewAllUsersAdmins(User user){ libraryControlStock.viewAllUsersAdmins(user); }
    public void viewActions(User user){ action.viewActions(user); }
    public void setPassword(User user) throws InterruptedException { libraryControlStock.setPassword(user); }

    public Book getBookById(int id){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getQuery)) {

            while (rs.next()) {
                int Id = rs.getInt("id");
                if(Id == id){
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    int year = rs.getInt("year");
                    int quantity = rs.getInt("quantity");
                    return new Book(Id, title, author, year, quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User checkUser(String username, String password){
        String getAdmin = "SELECT id, username, password, status FROM public.admins";
        String getUser = "SELECT id, username, password, status, borrowedbooks FROM public.users";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getAdmin)) {

            while (rs.next()) {
                String name = rs.getString("username");
                String pass = rs.getString("password");

                if(name.equals(username) && Objects.equals(pass, password)){
                    int id = rs.getInt("id");
                    return new User(id, name, password, 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUser)) {

            while (rs.next()) {
                String name = rs.getString("username");
                String pass = rs.getString("password");

                if(Objects.equals(name, username) && Objects.equals(pass, password)){
                    int id = rs.getInt("id");
                    return new User(id, name, password, 2);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean checkUser(String username){
        String getAdmin = "SELECT username FROM public.admins";
        String getUser = "SELECT username FROM public.users";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getAdmin);
             Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(getUser)) {

            while (rs.next()) {
                String name = rs.getString("username");
                if(Objects.equals(name, username)){
                    return false;
                }
            }
            while (rs1.next()) {
                String name = rs1.getString("username");
                if(Objects.equals(name, username)){
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}

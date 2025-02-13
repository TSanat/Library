import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class User {
    private int id;
    private String username;
    private String password;

    private int status;
    //Status == 1 is Admin
    //Status == 2 is User
    public ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(int id, String name, String password, int status) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.status = status;
    }
    public void addBook(Book book){
        borrowedBooks.add(book);
    }
    public void removeBook(Book book){
        borrowedBooks.remove(book);
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

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){ this.password = password; }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}

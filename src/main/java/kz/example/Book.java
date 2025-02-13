package kz.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Book extends SqlDatas{
    private int id;
    private String title;
    private String author;
    private int year;
    private int quantity;
    private static final String set = "UPDATE public.books SET quantity=? WHERE id = ?";

    public Book(int id, String title, String author, int year, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity;
    }

    public String printInfo() {
        return "Title: " + title + '\n' +
                "Author: " + author + '\n' +
                "Year: " + year + '\n' +
                "In stock:  " + quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return !(quantity == 0);
    }

    public int getQuantity(){ return quantity; }

    public void addInStock(){
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstme = conn.prepareStatement(set);){
            pstme.setInt(1, getQuantity() + 1);
            pstme.setInt(2, id);
            pstme.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delInStock(){
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstme = conn.prepareStatement(set);){
            pstme.setInt(1, getQuantity() - 1);
            pstme.setInt(2, id);
            pstme.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

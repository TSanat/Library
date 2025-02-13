package kz.example;
import java.sql.*;
import java.util.*;

public class LibraryControlUsers extends SqlDatas{

    private final Action action;
    public LibraryControlUsers(Action action) {
        this.action = action;
    }
    public void addAdmin(User user) throws InterruptedException {
        String query = "INSERT INTO public.admins(id, username, password, status) VALUES (?, ?, ?, ?);";
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
            action.addAction("Admin " + user.getUsername() + " registered");
            adminId++;

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void removeAdmin(User admin) throws InterruptedException {
        String query = "DELETE FROM public.admins WHERE id = ?;";
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, admin.getId());
            pstmt.executeUpdate();
            action.addAction("Admin " + admin.getUsername() + " removed");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean addUser(User user) throws InterruptedException {
        String query = "INSERT INTO public.users(id, username, password, status, borrowedbooks) VALUES (?, ?, ?, ?, ?);";
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)){
            Integer[] arr = new Integer[0];
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, 2);
            Array sqlArray = conn.createArrayOf("integer", arr);
            pstmt.setArray(5, sqlArray);
            pstmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean removeUser(User user, Library library){
        String query = "DELETE FROM public.users WHERE id = ?;";
        ArrayList<Long> arr = user.getBorrowedBooks();
        for(long l : arr){
            Book book = library.getBookById((int) l);
            user.removeBook(book);
            book.addInStock();
            System.out.println("Book " + book.getTitle() + " was returned successfully!!!");
            action.addAction("Book " + book.getId() + " returnedBy" + user.getUsername());
        }
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, user.getId());
            action.addAction("User " + user.getUsername() + " removed");
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void viewAllUsersAdmins(User user){
        String getAdmin = "SELECT username FROM public.admins;";
        String getUsers = "SELECT username FROM public.users;";

        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(getAdmin);
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(getUsers)){
            System.out.println("Admins: ");
            while (rs.next()){
                System.out.println("   Username: " + rs.getString("username"));
            }
            System.out.println("Users: ");
            while (rs1.next()){
                System.out.println("   Username: " + rs1.getString("username"));
            }
            action.addAction("Admin " + user.getUsername() + " viewedAllUser");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void setPassword(User user) throws InterruptedException {
        String updateUser = "UPDATE public.users SET password=? WHERE id = ?;";
        String updateAdmin = "UPDATE public.admins SET password=? WHERE id = ?;";
        System.out.print("Enter the old password: ");
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        if(s.equals(user.getPassword())){
            System.out.print("Enter the new password: ");
            s = scan.next();
            user.setPassword(s);
            if(user.getStatus() == 1){
                try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(updateAdmin)){
                    pstmt.setString(1, s);
                    pstmt.setInt(2, user.getId());
                    pstmt.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                    PreparedStatement pstmt = conn.prepareStatement(updateUser)){
                    pstmt.setString(1, s);
                    pstmt.setInt(2, user.getId());
                    pstmt.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            action.addAction("User(Admin) " + user.getUsername() + " changedPassword");
        }
        else{
            System.err.println("Password is incorrect!! Please try again LATER!!!");
            Thread.sleep(300);
        }
    }
}

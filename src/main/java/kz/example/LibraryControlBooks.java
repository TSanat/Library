package kz.example;
import java.sql.*;


public class LibraryControlBooks extends SqlDatas{
    private final String getQuery = "SELECT id, title, author, year, quantity FROM public.books";
    private final String insert = "INSERT INTO public.books(id, title, author, year, quantity) VALUES (?, ?, ?, ?, ?);";
    private final String delete = "DELETE FROM public.books WHERE id = ?";
    private final Action action;
    private final ControlViewOptions cv = new ControlViewOptions();

    public LibraryControlBooks(Action action) {
        this.action = action;
    }

    public void addBook(Book book, User user){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insert)) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getYear());
            pstmt.setInt(5, book.getQuantity());

            pstmt.executeUpdate();
            user.addBook(book);
            bookId++;
            action.addAction("Book " + book.getId() + " addedByAdmin" + user.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeBook(Book book, User user) throws InterruptedException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(delete)) {
            pstmt.setInt(1, book.getId());
            pstmt.executeUpdate();
            System.out.println("Book with id " + book.getId() + " have been removed!!!");
            action.addAction("Book " + book.getId() + " removedByAdmin" + user.getUsername());
            user.removeBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(Book book, User user) throws InterruptedException {
        if(book == null){
            System.err.println("Maybe you are mistaken by ID!!");
            Thread.sleep(300);
        }
        else if(!book.isAvailable()){
            System.err.println("Book is not in stock!!");
            Thread.sleep(300);
        }
        else if(user.getBorrowedBooks().contains((long)book.getId())){
            System.err.println("You already book this book");
        }
        else{
            user.addBook(book);
            book.delInStock();
            System.out.println("Book " + book.getTitle() + " was borrowed successfully!!!");
            action.addAction("Book " + book.getId() + " borrowedBy" + user.getUsername());
        }
    }
    public void returnBook(Book book, User user) throws InterruptedException {
        Long n = (long) book.getId();
        if (user.getBorrowedBooks().contains(n)) {
            user.removeBook(book);
            book.addInStock();
            System.out.println("Book " + book.getTitle() + " was returned successfully!!!");
            action.addAction("Book " + book.getId() + " returnedBy" + user.getUsername());
        } else {
            System.err.println("Maybe you have the wrong book!!!");
            Thread.sleep(300);
        }
    }
    public void myBorrowedBooks(User user){
        action.addAction("User " + user.getUsername() + " seedBorrowedBooks");
        System.out.println("My books: ");
        int sum = 1;
        for(Long id : user.getBorrowedBooks()){
            try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, title, author, year FROM public.books")){

                while (rs.next()){
                    Long Id = (long) rs.getInt("id");
                    if(Id.equals(id)){
                        String title = rs.getString("title");
                        String author = rs.getString("author");
                        int year = rs.getInt("year");
                        cv.viewBook(sum, Math.toIntExact(Id), title, author, year);
                        sum++;
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
    public void viewAllBooks(User user){
        int n = 1;
        action.addAction("User(Admin) " + user.getUsername() + " seedListBooks");
        System.out.println("All books list: ");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getQuery)) {
            int sum = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                cv.viewBook(sum, id, title, author, year);
                sum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewAvailableBooks(User user){
        int n = 1;
        action.addAction("User(Admin) " + user.getUsername() + " seeListAvailableBooks");
        System.out.println("Available books list: ");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getQuery)) {
            int sum = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                int quantity = rs.getInt("quantity");
                if(quantity > 0){
                    cv.viewBook(sum, id, title, author, year);
                    sum++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

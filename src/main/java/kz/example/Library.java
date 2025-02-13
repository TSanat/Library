import java.util.ArrayList;
public class Library {
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<User> admins = new ArrayList<>();
    private Action action;
    private LibraryControlUsers libraryControlStock;
    private LibraryControlBooks libraryControlBooks;



    public Library(Action action) {
        this.action = action;
        libraryControlStock = new LibraryControlUsers(users, admins, action);
        libraryControlBooks = new LibraryControlBooks(books, action);
    }

    public void addAdmin(User user) throws InterruptedException { libraryControlStock.addAdmin(user); }
    public void removeAdmin(User user) throws InterruptedException { libraryControlStock.removeAdmin(user); }
    public boolean addUser(User user) throws InterruptedException { return libraryControlStock.addUser(user); }
    public boolean removeUser(User user) throws InterruptedException { return libraryControlStock.removeUser(user); }
    public void getInfo(User user){ libraryControlStock.getInfo(user); }


    public void addBook(Book book){ libraryControlBooks.addBook(book); }
    public void removeBook(int id) throws InterruptedException {
        Book book = getBookById(id);
        libraryControlBooks.removeBook(book);
    }
    public void getInfo(Book book){
        libraryControlBooks.getInfo(book);
    }
    public void borrowBook(Book book, User user) throws InterruptedException {
        libraryControlBooks.borrowBook(book, user);
    }
    public void returnBook(Book book, User user) throws InterruptedException {
        libraryControlBooks.returnBook(book, user);
    }
    public void myBorrowedBooks(User user){ libraryControlBooks.myBorrowedBooks(user); }
    public void viewAllBooks(User user){ libraryControlBooks.viewAllBooks(user); }
    public void viewAvailableBooks(User user){ libraryControlBooks.viewAvailableBooks(user); }
    public void viewAllUsersAdmins(User user){ libraryControlStock.viewAllUsersAdmins(user); }
    public void viewActions(User user){ action.viewActions(user); }

    public Book getBookById(int id){
        for(Book book : books){
            if(book.getId() == id){
                return book;
            }
        }
        return null;
    }

    public void setPassword(User user) throws InterruptedException {
        libraryControlStock.setPassword(user);
    }
    public User checkUser(String username, String password){
        for(User user : users){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        for(User user : admins){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }
}

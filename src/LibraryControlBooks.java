import java.util.ArrayList;

public class LibraryControlBooks {
    private final ArrayList<Book> books;
    private final Action action;

    public LibraryControlBooks(ArrayList<Book> books, Action action) {
        this.books = books;
        this.action = action;
    }

    public void addBook(Book book){
        books.add(book);
        ControlAuth.bookId++;
        action.addAction(book.getTitle() + " was added at");
    }
    public void removeBook(Book book) throws InterruptedException {
        if(book == null){
            System.err.println("Book is not exist!!!");
            Thread.sleep(300);
        }
        else{
            books.remove(book);
            System.out.println("Book with id " + book.getId() + " have been removed!!!");
            action.addAction("Book " + book.getTitle() + " was removed at");
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
        else{
            user.addBook(book);
            book.delInStock();
            System.out.println("Book " + book.getTitle() + " was borrowed successfully!!!");
            action.addAction("Book " + book.getTitle() + " was borrowed by " + user.getUsername() + " at");
        }
    }
    public void returnBook(Book book, User user) throws InterruptedException {
        if(!user.getBorrowedBooks().contains(book)){
            System.err.println("Maybe you have the wrong book!!!");
            Thread.sleep(300);
        }
        else{
            user.removeBook(book);
            book.addInStock();
            System.out.println("Book " + book.getTitle() + " was returned successfully!!!");
            action.addAction("Book " + book.getTitle() + " was returned by " + user.getUsername() + " at");
        }
    }
    public void myBorrowedBooks(User user){
        action.addAction(user.getUsername() + " was requested to see borrowed books at");
        System.out.println("My books: ");
        int n = 1;
        for(Book book : user.borrowedBooks){
            System.out.println(n+ ") ID: " + book.getId() + "\n   Title: " + book.getTitle() + "\n   Author: " + book.getAuthor());
            n++;
        }
    }
    public void getInfo(Book book){
    }
    public void viewAllBooks(User user){
        int n = 1;
        action.addAction("User(Admin) " + user.getUsername() + " requested to see the list of all books");
        System.out.println("All books list: ");
        for(Book book : books){
            System.out.println(n+ ") Id: " + book.getId() +  "\n   Title: " + book.getTitle() + " \n   Author: " + book.getAuthor());
            n++;
        }
    }
    public void viewAvailableBooks(User user){
        int n = 1;
        action.addAction("User(Admin) " + user.getUsername() + " requested to see the list of all available books");
        System.out.println("Available books list: ");
        for(Book book : books){
            if(book.getQuantity() > 0){
                System.out.println(n+ ") Id: " + book.getId() +  "\n   Title: " + book.getTitle() + " \n   Author: " + book.getAuthor());
                n++;
            }
        }
    }
}

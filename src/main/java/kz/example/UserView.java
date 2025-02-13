package kz.example;
import java.util.Scanner;

public class UserView {
    private final Scanner scan = new Scanner(System.in);
    private final Library library;
    private final ControlViewOptions cv;
    private Action action;
    public UserView(Library library, ControlViewOptions cv, Action action) {
        this.library = library;
        this.cv = cv;
        this.action = action;
    }

    public void run(User user) throws InterruptedException {
        userPage(user);
    }
    private void userPage(User user) throws InterruptedException {
        action.addAction("User " + user.getUsername() + " signed");
        while (true){
            Thread.sleep(300);
            cv.showUserOptions();
            if(scan.hasNextInt()){
                int choose = scan.nextInt();
                switch (choose){
                    case 1:
                        library.getInfo(user);
                        break;
                    case 2:
                        library.viewAvailableBooks(user);
                        System.out.print("Enter ID of the book to borrow: ");
                        if(scan.hasNextInt()){
                            int n = scan.nextInt();
                            library.borrowBook(library.getBookById(n), user);
                        }
                        else {
                            cv.numberMessage();
                        }
                        break;
                    case 3:
                        System.out.println();
                        library.myBorrowedBooks(user);
                        System.out.print("Enter ID of the book to borrow: ");
                        if(scan.hasNextInt()){
                            int n = scan.nextInt();
                            library.returnBook(library.getBookById(n), user);
                        }
                        else{
                            cv.numberMessage();
                        }
                        break;
                    case 4:
                        library.viewAllBooks(user);
                        break;
                    case 5:
                        library.viewAvailableBooks(user);
                        break;
                    case 6:
                        library.myBorrowedBooks(user);
                        break;
                    case 7:
                        library.setPassword(user);
                        break;
                    case 8:
                        if(library.removeUser(user)){
                            ControlAuth.autorisation();
                        }
                        break;
                    case 9:
                        action.addAction("User " + user.getUsername() + " backToAutPage");
                        ControlAuth.autorisation();
                        break;
                    default:
                        System.out.println("Please enter number between 1 and 9");
                }
            }
            else{
                cv.numberMessage();
                Thread.sleep(300);
            }

        }


    }
}

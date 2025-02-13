package kz.example;

import java.util.Scanner;

public class AdminView {
    private final Scanner scan = new Scanner(System.in);
    private final Library library;
    private final ControlViewOptions cv;
    private Action action;
    public AdminView(Library library, ControlViewOptions cv, Action action) {
        this.library = library;
        this.cv = cv;
        this.action = action;
    }

    public void run(User user) throws InterruptedException {
        adminPage(user);
    }
    private void adminPage(User user) throws InterruptedException {
        action.addAction("Admin " + user.getUsername() + " signed");
        while (true){
            Thread.sleep(300);
            cv.showAdminOptions();
            if(scan.hasNextInt()){
                int choose = scan.nextInt();
                switch (choose){
                    case 1:
                        library.getInfo(user);
                        break;
                    case 2:
                        System.out.println("Enter title, author, year and quantity of the new book in new line: ");
                        String s = scan.nextLine();
                        String title = scan.nextLine();
                        String author = scan.nextLine();
                        int year;
                        if(scan.hasNextInt()){
                            year = scan.nextInt();
                        }else {
                            cv.numberMessage();
                            Thread.sleep(300);
                            break;
                        }
                        int quantity;
                        if(scan.hasNextInt()){
                            quantity = scan.nextInt();
                        }else {
                            cv.numberMessage();
                            Thread.sleep(300);
                            break;
                        }
                        library.addBook(new Book(ControlAuth.bookId, title, author, year, quantity), user);
                        break;
                    case 3:
                        library.viewAllBooks(user);
                        System.out.print("Enter id to remove a book: ");
                        if(scan.hasNextInt()){
                            int id = scan.nextInt();
                            library.removeBook(id, user);
                        }
                        else {
                            cv.numberMessage();
                            Thread.sleep(300);
                        }
                        break;
                    case 4:
                        library.viewAllBooks(user);
                        break;
                    case 5:
                        library.viewAvailableBooks(user);
                        break;
                    case 6:
                        library.viewAllUsersAdmins(user);
                        break;
                    case 7:
                        library.viewActions(user);
                        break;
                    case 8:
                        library.setPassword(user);
                        break;
                    case 9:
                        library.removeAdmin(user);
                        ControlAuth.autorisation();
                        break;
                    case 10:
                        action.addAction("User " + user.getUsername() + " backToAutPage");
                        ControlAuth.autorisation();
                        break;
                    default:
                        System.err.println("Please enter number between 1 and 9!!!");
                        Thread.sleep(300);
                }
            }
            else{
                cv.numberMessage();
                String s = scan.nextLine();
                Thread.sleep(300);
            }
        }
    }
}

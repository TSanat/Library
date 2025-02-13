package kz.example;
import java.sql.*;
import java.util.Scanner;

public class ControlAuth extends SqlDatas{

    private static final Scanner scan = new Scanner(System.in);
    private static Library library;
    private static ControlViewOptions cv;
    private static UserView controlUser;
    private static AdminView controlAdmin;
    private static Action action;
    public ControlAuth(Library library, ControlViewOptions cv, Action action) throws InterruptedException, SQLException {
        ControlAuth.library = library;
        controlAdmin = new AdminView(library, cv, action);
        controlUser = new UserView(library, cv, action);
        ControlAuth.cv = cv;
        ControlAuth.action = action;
    }
    public void run() throws InterruptedException {
        autorisation();
    }
    public static void autorisation() throws InterruptedException {
        while (true) {
            System.out.println("\nChoose and enter the number \n 1) Sign in \n 2) Register \n 3) Exit");
            if(scan.hasNextInt()){
                int choose = scan.nextInt();
                switch (choose){
                    case 1:
                        signIn();
                        break;
                    case 2:
                        signUp();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Enter number between 1 and 3!!!");
                }
            }
            else {
                cv.numberMessage();
                String s = scan.nextLine();
            }
        }
    }
    public static void signIn() throws InterruptedException {
        while (true){
            System.out.print("\nUsername(To go back enter *exit* with Asterisk): ");
            String username = scan.next();
            if(username.equals("*exit*")){
                return;
            }
            System.out.print("Password: ");
            String password = scan.next();
            User user = library.checkUser(username, password);
            if(user != null){
                if(user.getStatus() == 1){
                    controlAdmin.run(user);
                }
                else {
                    controlUser.run(user);
                }
            }
            else {
                System.err.println("Username or password is incorrect!!!");
                Thread.sleep(300);
            }
        }

    }

    private static void signUp() throws InterruptedException {
        while (true){
            System.out.println("\nCome up with a Username and Password(Write *exit* with Asterisk to go back)!!");
            Thread.sleep(300);
            System.out.print("Username(Write without any space): ");
            String username = scan.next();
            if(username.equals("*exit*")){
                return;
            }
            if(library.checkUser(username)){
                System.out.print("Password(Write without any space): ");
                String password = scan.next();
                User user = new User(userId, username, password, 2);
                if(library.addUser(user)){
                    action.addAction("User " + user.getUsername() + " registered");
                    userId++;
                    controlUser.run(user);
                }
            }
            else {
                System.err.println("This username is already taken!!!");
                Thread.sleep(300);
            }
        }
    }
}
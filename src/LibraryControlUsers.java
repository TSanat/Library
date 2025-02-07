import java.util.ArrayList;
import java.util.Scanner;

public class LibraryControlUsers {
    private final ArrayList<User> users;
    private final ArrayList<User> admins;
    private final Action action;
    public LibraryControlUsers(ArrayList<User> users, ArrayList<User> admins, Action action) {
        this.admins = admins;
        this.users = users;
        this.action = action;
    }
    public void addAdmin(User user) throws InterruptedException {
        for(User u : users){
            if(u.getUsername().equals(user.getUsername())){
                System.err.println(user.getUsername() + " have already exist!!!");
                Thread.sleep(300);
                return;
            }
        }
        admins.add(user);
        action.addAction(user.getUsername() + " was registered at");
        ControlAuth.userId++;
    }
    public void removeAdmin(User admin) throws InterruptedException {
        if(admin == null){
            System.err.println("Admin is not exist!!!");
            Thread.sleep(300);
        }
        else{
            admins.remove(admin);
            System.out.println("Admin with id " + admin.getId() + " have been removed!!!");
            action.addAction("Admin " + admin.getUsername() + " was removed at");
        }
    }
    public boolean addUser(User user) throws InterruptedException {
        for(User user1 : users){
            if(user.getUsername().equals(user1.getUsername())){
                System.err.println(user.getUsername() + " have already exist!!!");
                Thread.sleep(300);
                return false;
            }
        }
        users.add(user);
        action.addAction("User " + user.getUsername() + " was added at");
        ControlAuth.userId++;
        System.out.println(user.getUsername() + " have been added!!!");
        return true;
    }
    public boolean removeUser(User user) throws InterruptedException {
        if(user == null){
            System.err.println("User is not exist!!!");
            Thread.sleep(300);
            return false;
        }
        else{
            users.remove(user);
            action.addAction("User " + user.getUsername() + " was removed at");
            System.out.println(user.getUsername() + " have been removed!!!");
            return true;
        }
    }
    public void viewAllUsersAdmins(User user){
        action.addAction("Admin " + user.getUsername() + " requested to see the list of all users and admins");
        int ia = 1;
        System.out.println("Admins: ");
        for(User admin : admins){
            System.out.println(ia + ": " + admin.getUsername());
            ia++;
        }
        ia = 1;
        System.out.println("\nUsers: ");
        for(User user1 : users){
            System.out.println(ia + ": " + user1.getUsername());
            ia++;
        }
    }
    public void setPassword(User user) throws InterruptedException {
        System.out.print("Enter the old password: ");
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        if(s.equals(user.getPassword())){
            System.out.print("Enter the new password: ");
            s = scan.next();
            user.setPassword(s);
            action.addAction(user.getUsername() + " changed password at");
        }
        else{
            System.err.println("Password is incorrect!! Please try again LATER!!!");
            Thread.sleep(300);
        }
    }
    public void getInfo(User user){
        System.out.print("\nYour ID: " + user.getId() + "\n" + "Your username: " + user.getUsername() + "\n" + "Your status: " );
        if(user.getStatus() == 1) System.out.print("Admin");
        else System.out.print("User");
        System.out.println();
    }
}

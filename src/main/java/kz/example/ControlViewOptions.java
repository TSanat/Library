public class ControlViewOptions {
    public ControlViewOptions() {
    }

    public void showAdminOptions(){
        System.out.println("""

                Options:\s
                1) Details\s
                2) Add book\s
                3) Remove book\s
                4) View all books\s
                5) View all available books\s
                6) View all users and admins\s
                7) View all actions in Library
                8) Change password\s
                9) Delete account\s
                10) Back
                """);
    }

    public void showUserOptions(){
        System.out.println("""
                
                Options:\s
                1) Details\s
                2) Borrow book\s
                3) Return book\s
                4) View all books\s
                5) View all available books\s
                6) View my books\s
                7) Change password\s
                8) Change username\s
                9) Delete account\s
                10) Back
                """);
    }

    public void numberMessage(){
        System.out.println("It must be the number!!!");
    }
}

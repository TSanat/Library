package kz.example;

import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws InterruptedException, SQLException {
        Action action = new Action();

        Library library = new Library(action);
        ControlViewOptions cv = new ControlViewOptions();

        ControlAuth controller = new ControlAuth(library, cv, action);
        controller.run();
    }
}

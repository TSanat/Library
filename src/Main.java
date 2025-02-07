public class Main {
    public static void main(String[] args) throws Exception {

        Action action = new Action();
        action.addAction("Library was created at");

        Library library = new Library(action);
        ControlViewOptions cv = new ControlViewOptions();

        ControlAuth controller = new ControlAuth(library, cv, action);
        controller.run();
    }
}
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Action {
    private ArrayList<String> actions = new ArrayList<>();

    public Action() {
    }

    public void addAction(String action){
        action = action + " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd 'and' HH:mm:ss"));
        actions.add(action);
    }
    public void viewActions(User user){
        addAction("Admin " + user.getUsername() + " requested to see the list of all actions in Library");
        for(String action : actions){
            System.out.println(action);
        }
    }
}

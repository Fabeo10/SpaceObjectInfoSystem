import java.util.HashMap;
import java.util.Map;

public class Administrator {
    private Map<String, User> userMap = new HashMap<>();

    public void createUser(String id, String userType) {
        try {
            User user = UserFactory.createUser(userType);
            userMap.put(id, user);
            System.out.println("User with id: < " + id + " > created.");
        }

        catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manageUser(String id) {
        User user = userMap.get(id);
        if(user != null) {
            System.out.print("Managing user < " + id + " >: ");
            user.displayRole();
        }
        else {
            System.out.println("User not found for ID: < " + id + " >");
        }
    }

    public void deleteUser(String id) {
        if(userMap.remove(id) != null) {
            System.out.println("User with ID < " + id + " > has been deleted.");
        }
        else {
            System.out.println("User not found for ID: < " + id + " >");
        }
    }
}

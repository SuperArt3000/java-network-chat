package client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Zurbaevi Nika
 */
public class ClientGuiModel {
    private Set<String> allUserNames = new HashSet<>();

    protected Set<String> getAllUserNames() {
        return allUserNames;
    }

    protected void addUser(String userName) {
        allUserNames.add(userName);
    }

    protected void deleteUser(String userName) {
        allUserNames.remove(userName);
    }

    protected void setUsers(Set<String> users) {
        this.allUserNames = users;
    }
}

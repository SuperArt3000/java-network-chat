package server;

import connection.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zurbaevi Nika
 */
public class ServerGuiModel {
    private Map<String, Connection> allUsers = new HashMap<>();

    protected Map<String, Connection> getAllUsersChat() {
        return allUsers;
    }

    protected String getName(String nickname) {
        return allUsers.get(nickname).toString();
    }

    protected Connection getConnection(String nickname) {
        return allUsers.get(nickname);
    }

    protected void addUser(String nickname, Connection connection) {
        allUsers.put(nickname, connection);
    }

    protected void removeUser(String nickname) {
        allUsers.remove(nickname);
    }
}

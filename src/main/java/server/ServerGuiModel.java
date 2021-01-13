package server;

import connection.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zurbaevi Nika
 */
public class ServerGuiModel {
    private Map<String, Connection> allUsersMultiChat = new HashMap<>();

    protected Map<String, Connection> getAllUsersChat() {
        return allUsersMultiChat;
    }

    protected String getName(String nameUser) {
        return allUsersMultiChat.get(nameUser).toString();
    }

    protected Connection getConnection(String userName) {
        return allUsersMultiChat.get(userName);
    }

    protected void addUser(String nameUser, Connection connection) {
        allUsersMultiChat.put(nameUser, connection);
    }

    protected void removeUser(String nameUser) {
        allUsersMultiChat.remove(nameUser);
    }
}

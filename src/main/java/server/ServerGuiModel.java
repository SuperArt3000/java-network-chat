package server;

import connection.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс так называемая модель. Она содержит всю бизнес-логику приложения.
 *
 * @author Zurbaevi Nika
 */
public class ServerGuiModel {
    /**
     * Хранит карту со всеми подключившимися клиентами ключ - имя клиента, значение - объект connection
     */
    private Map<String, Connection> allUsersMultiChat = new HashMap<>();

    /**
     * Возращает всех пользователей
     *
     * @return пользователи
     */
    protected Map<String, Connection> getAllUsersChat() {
        return allUsersMultiChat;
    }

    /**
     * Возращает имя пользователя
     *
     * @param nameUser имя поьзователя
     * @return имя пользователя
     */
    protected String getName(String nameUser) {
        return allUsersMultiChat.get(nameUser).toString();
    }

    protected Connection getConnection(String userName) {
        return allUsersMultiChat.get(userName);
    }


    /**
     * Добавляет пользователя
     *
     * @param nameUser   имя пользователя
     * @param connection пользователь
     */
    protected void addUser(String nameUser, Connection connection) {
        allUsersMultiChat.put(nameUser, connection);
    }

    /**
     * Удаляет пользователя
     *
     * @param nameUser имя пользователя
     */
    protected void removeUser(String nameUser) {
        allUsersMultiChat.remove(nameUser);
    }
}

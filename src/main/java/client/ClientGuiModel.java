package client;

import connection.Connection;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс так называемая модель. Она содержит всю бизнес-логику приложения.
 *
 * @author Zurbaevi Nika
 */
public class ClientGuiModel {
    /**
     * Хранится множетство подключившихся пользователей
     */
    private Set<String> allUserNames = new HashSet<>();

    /**
     * Возращает всех подключившихся пользователей
     *
     * @return подключившихся пользователей
     */
    protected Set<String> getAllUserNames() {
        return allUserNames;
    }

    /**
     * Добаввляет пользователя в список
     *
     * @param userName имя пользователя
     */
    protected void addUser(String userName) {
        allUserNames.add(userName);
    }

    /**
     * удаляет пользователя
     *
     * @param userName имя пользователя котоый будет удален
     */
    protected void deleteUser(String userName) {
        allUserNames.remove(userName);
    }

    /**
     * Устанавливает всех подключившихся пользователей
     *
     * @param users пользователи
     */
    protected void setUsers(Set<String> users) {
        this.allUserNames = users;
    }
}

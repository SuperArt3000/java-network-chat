package connection;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Zurbaevi Nika
 */
public class Message implements Serializable {
    /**
     * Тип сообщения
     */
    private MessageType typeMessage;
    /**
     * Текст сообщения
     */
    private String textMessage;
    /**
     * Множество имен уже подключившихся пользователей
     */
    private Set<String> listUsers;

    /**
     * Конструктор с определенными значениями
     *
     * @param typeMessage тип сообщении
     * @param textMessage само сообщение
     */
    public Message(MessageType typeMessage, String textMessage) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
        this.listUsers = null;
    }

    /**
     * Конструктор с определенными значениями
     *
     * @param typeMessage тип сообщении
     * @param listUsers   множество имен уже подключившихся пользователей
     */
    public Message(MessageType typeMessage, Set<String> listUsers) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = listUsers;
    }

    /**
     * Конструктор с определенными значениями
     *
     * @param typeMessage тип сообщении
     */
    public Message(MessageType typeMessage) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = null;
    }

    public Message(MessageType typeMessage, String[] text) {
        this.typeMessage = typeMessage;
        this.textMessage = text[0] + " " + text[1];
        this.listUsers = null;
    }

    /**
     * Возращает тип сообщении
     *
     * @return тип сообщении
     */
    public MessageType getTypeMessage() {
        return typeMessage;
    }

    /**
     * Возращает множество подключившихся пользователей
     *
     * @return множество подключившихся пользователей
     */
    public Set<String> getListUsers() {
        return listUsers;
    }

    /**
     * Возращает само сообщение
     *
     * @return само сообщение
     */
    public String getTextMessage() {
        return textMessage;
    }

}

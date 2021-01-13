package connection;

import java.io.*;
import java.net.Socket;

/**
 * @author Zurbaevi Nika
 */
public class Connection implements Closeable {
    /**
     * Подключение сокета к серверу
     */
    private final Socket socket;
    /**
     * Писать из сокета.
     */
    private final ObjectOutputStream out;
    /**
     * Читать из сокета.
     */
    private final ObjectInputStream in;

    /**
     * Конструктор с определенными значениями
     *
     * @param socket подключение сокета к серверу
     * @throws IOException если при чтении/записи заголовка потока возникает ошибка ввода-вывода
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Метод, отправляющий по сокетному соединению сообщение
     *
     * @param message сообщение которое будет отправленно
     * @throws IOException если при чтении/записи заголовка потока возникает ошибка ввода-вывода
     */
    public void send(Message message) throws IOException {
        synchronized (this.out) {
            out.writeObject(message);
        }
    }

    /**
     * Метод, принимающий сообщение по сокетному соединению
     *
     * @return сообщение типа Message
     * @throws IOException            если при чтении/записи заголовка потока возникает ошибка ввода-вывода
     * @throws ClassNotFoundException Класс сериализованного объекта не может быть найденным
     */
    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (this.in) {
            return (Message) in.readObject();
        }
    }

    /**
     * Метод, зарывающий потоки чтения, записи и сокет
     *
     * @throws IOException если при чтении/записи заголовка потока возникает ошибка ввода-вывода
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}

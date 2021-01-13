package client;


import connection.Connection;
import connection.Message;
import connection.MessageType;
import database.SQLService;
import sound.MakeSound;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Zurbaevi Nika
 */
public class ClientGuiController {
    private Connection connection;
    private ClientGuiModel model;
    private ClientGuiView view;

    private volatile boolean clientConnected;
    private String userName;
    private boolean isDatabaseConnected;

    public static void main(String[] args) {
        ClientGuiController clientGuiController = new ClientGuiController();
        clientGuiController.run(clientGuiController);
    }

    public boolean isDatabaseConnected() {
        return isDatabaseConnected;
    }

    public void setDatabaseConnected(boolean databaseConnected) {
        isDatabaseConnected = databaseConnected;
    }

    public boolean isClientConnected() {
        return clientConnected;
    }

    public void setClientConnected(boolean clientConnected) {
        this.clientConnected = clientConnected;
    }

    public void run(ClientGuiController clientGuiController) {
        model = new ClientGuiModel();
        view = new ClientGuiView(clientGuiController);
        view.initComponents();
        if (!SQLService.connect()) {
            throw new RuntimeException("Failed to connect to database");
        }
        while (true) {
            if (clientGuiController.isClientConnected()) {
                clientGuiController.userNameRegistration();
                clientGuiController.receiveMessageFromServer();
                clientGuiController.setClientConnected(false);
            }
        }
    }

    protected void connectToServer() {
        if (!clientConnected) {
            while (true) {
                try {
                    connection = new Connection(new Socket(view.getServerAddress(), view.getPort()));
                    clientConnected = true;
                    view.addMessage("You have connected to the server.\n");
                    break;
                } catch (Exception e) {
                    view.errorDialogWindow("An error has occurred! Perhaps you entered the wrong server address or port. try again");
                    break;
                }
            }
        } else {
            view.errorDialogWindow("You are already connected!");
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    protected void userNameRegistration() {
        while (true) {
            try {
                Message message = connection.receive();
                if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {
                    userName = SQLService.getNickname(userName);
                    connection.send(new Message(MessageType.USER_NAME, userName));
                }
                if (message.getTypeMessage() == MessageType.NAME_USED) {
                    view.errorDialogWindow("A user with this name is already in the chat");
                    disableClient();
                    if (connection != null) connection.close();
                    break;
                }
                if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                    view.addMessage(String.format("Your name is accepted (%s)\n", userName));
                    model.setUsers(message.getListUsers());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.errorDialogWindow("An error occurred while registering the name. Try reconnecting");
                try {
                    connection.close();
                    clientConnected = false;
                    break;
                } catch (IOException ex) {
                    view.errorDialogWindow("Error closing connection");
                }
            }
        }
    }

    protected void sendMessageOnServer(String text) {
        try {
            connection.send(new Message(MessageType.TEXT_MESSAGE, text));
        } catch (Exception e) {
            view.errorDialogWindow("Error sending message");
        }
    }

    protected void sendPrivateMessageOnServer(String... data) {
        try {
            if (!userName.equals(data[0])) {
                view.addMessage(String.format("Private message sent to user (%s)\n", data[0]));
                connection.send(new Message(MessageType.PRIVATE_MESSAGE_TEXT, data));
            } else {
                view.errorDialogWindow("You cannot send a private message to yourself");
            }
        } catch (Exception e) {
            view.errorDialogWindow("Error sending message");
        }
    }

    protected void receiveMessageFromServer() {
        while (clientConnected) {
            try {
                Message message = connection.receive();
                if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                    view.addMessage(message.getTextMessage());
                }
                if (message.getTypeMessage() == MessageType.CHANGE_NAME_ACCEPTED) {
                    String[] data = message.getTextMessage().split(" ");
                    view.addMessage(message.getTextMessage() + "\n");
                    model.deleteUser(data[0]);
                    model.addUser(data[data.length - 1]);
                    view.refreshListUsers(model.getAllUserNames());
                }
                if (message.getTypeMessage() == MessageType.PRIVATE_MESSAGE_TEXT) {
                    String[] data = message.getTextMessage().split(" ");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < data.length - 1; i++) {
                        stringBuilder.append(data[i]).append(" ");
                    }
                    view.addMessage(String.format("Private message from (%s): %s\n", data[data.length - 1], stringBuilder.toString()));
                }
                if (message.getTypeMessage() == MessageType.USER_ADDED) {
                    model.addUser(message.getTextMessage());
                    MakeSound.playSound("connected.wav");
                    view.refreshListUsers(model.getAllUserNames());
                    view.addMessage(String.format("(%s) has joined the chat.\n", message.getTextMessage()));
                }
                if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                    model.deleteUser(message.getTextMessage());
                    MakeSound.playSound("disconnected.wav");
                    view.refreshListUsers(model.getAllUserNames());
                    view.addMessage(String.format("(%s) has left the chat.\n", message.getTextMessage()));
                }
            } catch (Exception e) {
                view.errorDialogWindow("An error occurred while receiving a message from the server.");
                setClientConnected(false);
                view.refreshListUsers(model.getAllUserNames());
                break;
            }
        }
    }

    protected void disableClient() {
        try {
            if (clientConnected) {
                connection.send(new Message(MessageType.DISABLE_USER));
                model.getAllUserNames().clear();
                clientConnected = false;
                view.refreshListUsers(model.getAllUserNames());
                view.addMessage("You have disconnected from the server.\n");
            } else {
                view.errorDialogWindow("You are already disconnected.");
            }
        } catch (Exception e) {
            view.errorDialogWindow("An error occurred while disconnecting.");
        }
    }

    public void changeUsername() throws IOException {
        String newUsername = view.getUserName();
        if (SQLService.changeNick(userName, newUsername)) {
            model.deleteUser(userName);
            userName = newUsername;
            model.addUser(newUsername);
            view.refreshListUsers(model.getAllUserNames());
            connection.send(new Message(MessageType.USERNAME_CHANGED, newUsername));
        }
    }
}

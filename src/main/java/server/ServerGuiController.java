package server;


import connection.Connection;
import connection.Message;
import connection.MessageType;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Zurbaevi Nika
 */
public class ServerGuiController {

    private ServerGuiView gui;
    private ServerGuiModel model;
    private ServerSocket serverSocket;

    private volatile boolean isServerStart;

    public static void main(String[] args) {
        ServerGuiController serverGuiController = new ServerGuiController();
        serverGuiController.run(serverGuiController);
    }

    public void run(ServerGuiController serverGuiController) {
        gui = new ServerGuiView(serverGuiController);
        model = new ServerGuiModel();
        gui.initComponents();
        while (true) {
            if (isServerStart) {
                serverGuiController.acceptServer();
                isServerStart = false;
            }
        }
    }

    protected void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isServerStart = true;
            gui.refreshDialogWindowServer("Server started.\n");
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Server failed to start.\n");
        }
    }

    protected void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                for (Map.Entry<String, Connection> user : model.getAllUsersChat().entrySet()) {
                    user.getValue().close();
                }
                serverSocket.close();
                model.getAllUsersChat().clear();
                gui.refreshDialogWindowServer("Server stopped.\n");
            } else {
                gui.refreshDialogWindowServer("The server is not running - there is nothing to stop!\n");
            }
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Server could not be stopped.\n");
        }
    }

    protected void acceptServer() {
        while (true) {
            try {
                new ServerThread(serverSocket.accept()).start();
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Server connection lost.\n");
                break;
            }
        }
    }

    protected void sendMessageAllUsers(Message message) {
        for (Map.Entry<String, Connection> user : model.getAllUsersChat().entrySet()) {
            try {
                user.getValue().send(message);
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Error sending message to all users!\n");
            }
        }
    }

    protected void refreshMap(Message message) {
        for (Map.Entry<String, Connection> user : model.getAllUsersChat().entrySet()) {
            try {
                user.getValue().send(message);
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Error sending message to all users!\n");
            }
        }
    }

    protected void sendPrivateMessage(Message message) {
        for (Map.Entry<String, Connection> user : model.getAllUsersChat().entrySet()) {
            try {
                String[] data = message.getTextMessage().split(" ");
                if (user.getKey().equals(data[0])) {
                    user.getValue().send(message);
                }
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Error sending message to all users!\n");
            }
        }
    }

    private class ServerThread extends Thread {
        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        private String requestAndAddingUser(Connection connection) {
            while (true) {
                try {
                    connection.send(new Message(MessageType.REQUEST_NAME_USER));
                    Message responseMessage = connection.receive();
                    String userName = responseMessage.getTextMessage();
                    if (responseMessage.getTypeMessage() == MessageType.USER_NAME && userName != null && !userName.isEmpty() && !model.getAllUsersChat().containsKey(userName)) {
                        model.addUser(userName, connection);
                        Set<String> listUsers = new HashSet<>();
                        for (Map.Entry<String, Connection> users : model.getAllUsersChat().entrySet()) {
                            listUsers.add(users.getKey());
                        }
                        connection.send(new Message(MessageType.NAME_ACCEPTED, listUsers));
                        sendMessageAllUsers(new Message(MessageType.USER_ADDED, userName));
                        return userName;
                    } else {
                        connection.send(new Message(MessageType.NAME_USED));
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer("There was an error requesting and adding a new user\n");
                    return null;
                }
            }
        }

        private void messagingBetweenUsers(Connection connection, String userName) {
            while (true) {
                try {
                    Message message = connection.receive();
                    if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                        String textMessage = String.format("%s: %s\n", userName, message.getTextMessage());
                        sendMessageAllUsers(new Message(MessageType.TEXT_MESSAGE, textMessage));
                    }
                    if (message.getTypeMessage() == MessageType.PRIVATE_MESSAGE_TEXT) {
                        sendPrivateMessage(new Message(MessageType.PRIVATE_MESSAGE_TEXT, message.getTextMessage() + " " + userName));
                    }
                    if (message.getTypeMessage() == MessageType.USERNAME_CHANGED) {
                        sendMessageAllUsers(new Message(MessageType.USERNAME_CHANGED, String.format("%s changed nickname to %s", userName, message.getTextMessage())));
                        String tempName = userName;
                        Connection tempConnection = model.getConnection(userName);
                        model.removeUser(tempName);
                        userName = message.getTextMessage();
                        model.addUser(userName, tempConnection);
                    }
                    if (message.getTypeMessage() == MessageType.DISABLE_USER) {
                        sendMessageAllUsers(new Message(MessageType.REMOVED_USER, userName));
                        model.removeUser(userName);
                        connection.close();
                        gui.refreshDialogWindowServer(String.format("Remote access user %s disconnected.\n", socket.getRemoteSocketAddress()));
                        break;
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer(String.format("An error occurred while sending a message from the user %s, either disconnected!\n", userName));
                    model.removeUser(userName);
                    break;
                }
            }
        }

        @Override
        public void run() {
            gui.refreshDialogWindowServer(String.format("A new user connected with a remote socket - %s.\n", socket.getRemoteSocketAddress()));
            try {
                Connection connection = new Connection(socket);
                messagingBetweenUsers(connection, requestAndAddingUser(connection));
            } catch (Exception e) {
                gui.refreshDialogWindowServer("An error occurred while sending a message from the user!\n");
            }
        }
    }
}

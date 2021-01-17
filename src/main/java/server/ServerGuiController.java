package server;


import connection.Message;
import connection.MessageType;
import connection.Network;

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
                for (Map.Entry<String, Network> user : model.getAllUsersChat().entrySet()) {
                    user.getValue().close();
                }
                serverSocket.close();
                model.getAllUsersChat().clear();
                isServerStart = false;
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
        for (Map.Entry<String, Network> user : model.getAllUsersChat().entrySet()) {
            try {
                user.getValue().send(message);
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Error sending message to all users!\n");
            }
        }
    }

    protected void sendPrivateMessage(Message message) {
        for (Map.Entry<String, Network> user : model.getAllUsersChat().entrySet()) {
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

    public boolean isServerStart() {
        return isServerStart;
    }

    private class ServerThread extends Thread {

        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        private String requestAndAddingUser(Network connection) {
            while (true) {
                try {
                    connection.send(new Message(MessageType.REQUEST_NAME_USER));
                    Message responseMessage = connection.receive();
                    String nickname = responseMessage.getTextMessage();
                    if (responseMessage.getTypeMessage() == MessageType.USER_NAME && nickname != null && !nickname.isEmpty() && !model.getAllUsersChat().containsKey(nickname)) {
                        model.addUser(nickname, connection);
                        Set<String> listUsers = new HashSet<>();
                        for (Map.Entry<String, Network> users : model.getAllUsersChat().entrySet()) {
                            listUsers.add(users.getKey());
                        }
                        connection.send(new Message(MessageType.NAME_ACCEPTED, listUsers));
                        sendMessageAllUsers(new Message(MessageType.USER_ADDED, nickname));
                        return nickname;
                    } else {
                        connection.send(new Message(MessageType.NAME_USED));
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer("There was an error requesting and adding a new user\n");
                    return null;
                }
            }
        }

        private void messagingBetweenUsers(Network network, String nickname) {
            while (true) {
                try {
                    Message message = network.receive();
                    if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                        String textMessage = String.format("%s: %s\n", nickname, message.getTextMessage());
                        sendMessageAllUsers(new Message(MessageType.TEXT_MESSAGE, textMessage));
                    }
                    if (message.getTypeMessage() == MessageType.PRIVATE_TEXT_MESSAGE) {
                        sendPrivateMessage(new Message(MessageType.PRIVATE_TEXT_MESSAGE, message.getTextMessage() + " " + nickname));
                    }
                    if (message.getTypeMessage() == MessageType.USERNAME_CHANGED) {
                        sendMessageAllUsers(new Message(MessageType.USERNAME_CHANGED, String.format("%s changed nickname to %s", nickname, message.getTextMessage())));
                        String tempName = nickname;
                        Network tempConnection = model.getConnection(nickname);
                        model.removeUser(tempName);
                        nickname = message.getTextMessage();
                        model.addUser(nickname, tempConnection);
                    }
                    if (message.getTypeMessage() == MessageType.DISABLE_USER) {
                        sendMessageAllUsers(new Message(MessageType.REMOVED_USER, nickname));
                        model.removeUser(nickname);
                        network.close();
                        gui.refreshDialogWindowServer(String.format("Remote access user %s disconnected.\n", socket.getRemoteSocketAddress()));
                        break;
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer(String.format("An error occurred while sending a message from the user %s, either disconnected!\n", nickname));
                    model.removeUser(nickname);
                    break;
                }
            }
        }

        @Override
        public void run() {
            gui.refreshDialogWindowServer(String.format("A new user connected with a remote socket - %s.\n", socket.getRemoteSocketAddress()));
            try {
                Network connection = new Network(socket);
                messagingBetweenUsers(connection, requestAndAddingUser(connection));
            } catch (Exception e) {
                gui.refreshDialogWindowServer("An error occurred while sending a message from the user!\n");
            }
        }

    }
}

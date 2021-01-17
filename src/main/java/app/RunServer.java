package app;

import server.ServerGuiController;

public class RunServer {
    public static void main(String[] args) {
        ServerGuiController serverGuiController = new ServerGuiController();
        serverGuiController.run(serverGuiController);
    }
}

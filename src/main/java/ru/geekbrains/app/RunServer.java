package ru.geekbrains.app;

import ru.geekbrains.server.ServerGuiController;

/**
 * @author Zurbaevi Nika
 */
public class RunServer {
    public static void main(String[] args) {
        ServerGuiController serverGuiController = new ServerGuiController();
        serverGuiController.run(serverGuiController);
    }
}

package app;

import client.ClientGuiController;

/**
 * @author Zurbaevi Nika
 */
public class RunClient {
    public static void main(String[] args) {
        ClientGuiController clientGuiController = new ClientGuiController();
        clientGuiController.run(clientGuiController);
    }
}

package client;

import database.SQLService;
import sound.MakeSound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

/**
 * Данный класс отвечает за отображение данных пользователю.
 * Все, что видит пользователь, генерируется видом.
 *
 * @author Zurbaevi Nika
 */
public class ClientGuiView extends JFrame {

    private final ClientGuiController client;

    private javax.swing.JButton buttonChangeInputColor;
    private javax.swing.JButton buttonChangeName;
    private javax.swing.JButton buttonChatLog;
    private javax.swing.JButton buttonConnectionToServer;
    private javax.swing.JButton buttonDisconnectToServer;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton buttonMoveToSystemTray;
    private javax.swing.JButton buttonRegistration;
    private javax.swing.JButton buttonSend;
    private javax.swing.JButton buttonSignIn;
    private javax.swing.JButton buttonSignOut;
    private javax.swing.JButton buttonSoundOptions;
    private javax.swing.JList<String> listUserOnline;
    private javax.swing.JRadioButton radioButtonSendMessageToAll;
    private javax.swing.JRadioButton radioButtonSendPrivateMessageToSelectedUser;
    private javax.swing.JScrollPane scrollPanelForChatLog;
    private javax.swing.JScrollPane scrollPanelForUserListOnline;
    private javax.swing.JTextArea textAreaChatLog;
    private javax.swing.JTextField textFieldUserInputMessage;

    private boolean radioButtonCheckPrivateOrNot;
    private JMenuItem menuItemChangeUsername;
    private JMenu menuOthers;

    /**
     * Конструктор c определенными значениями
     *
     * @param clientGuiController клиент
     */
    public ClientGuiView(ClientGuiController clientGuiController) {
        this.client = clientGuiController;

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGuiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Метод инициализирующий графический интерфейс клиентского приложения
     */
    protected void initComponents() {
//        buttonGroup = new ButtonGroup();
//        radioButtonSendMessageToAll = new JRadioButton();
//        radioButtonSendPrivateMessageToSelectedUser = new JRadioButton();
//        buttonClearChatLog = new JButton();
//        buttonChangeInputColor = new JButton();
//        buttonSoundOptions = new JButton();
//        buttonChatLog = new JButton();
//        buttonMoveToSystemTray = new JButton();
//        buttonSend = new JButton();
//        textFieldUserInputMessage = new JTextField();
//        scrollPanelForUserListOnline = new JScrollPane();
//        listUserOnline = new JList<>();
//        buttonConnectToServer = new JButton();
//        scrollPanelForChatLog = new JScrollPane();
//        textAreaChatLog = new JTextArea();
//        buttonDisconnectToServer = new JButton();
//        menuBar = new JMenuBar();
//        menuDatabase = new JMenu();
//        menuItemLogin = new JMenuItem();
//        menuItemRegistration = new JMenuItem();
//        menuItemChangeUsername = new javax.swing.JMenuItem();
//        menuOthers = new javax.swing.JMenu();
//
//        setTitle("Network chat");
//
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setMinimumSize(new Dimension(758, 400));
//        setPreferredSize(new Dimension(758, 400));
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                if (client.isClientConnected()) {
//                    client.disableClient();
//                }
//                System.exit(0);
//            }
//        });
//
//        buttonGroup.add(radioButtonSendMessageToAll);
//        radioButtonSendMessageToAll.setSelected(true);
//        radioButtonSendMessageToAll.setText("Send message to all");
//        radioButtonSendMessageToAll.addActionListener(e -> radioButtonCheckPrivateOrNot = false);
//
//        buttonGroup.add(radioButtonSendPrivateMessageToSelectedUser);
//        radioButtonSendPrivateMessageToSelectedUser.setText("Send private message to selected user ");
//        radioButtonSendPrivateMessageToSelectedUser.addActionListener(e -> radioButtonCheckPrivateOrNot = true);
//
//        try {
//            setIconImage(ImageIO.read(new File("default.png")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        buttonClearChatLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/clear-chat.png")))); // NOI18N
//        buttonClearChatLog.setToolTipText("Clear chat");
//        buttonClearChatLog.setMaximumSize(new Dimension(50, 25));
//        buttonClearChatLog.setMinimumSize(new Dimension(50, 25));
//        buttonClearChatLog.setPreferredSize(new Dimension(50, 25));
//        buttonClearChatLog.addActionListener(e -> {
//            if (!textAreaChatLog.getText().equals("")) {
//                textAreaChatLog.setText("");
//            }
//        });
//
//        buttonChangeInputColor.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/color-wheel.png")))); // NOI18N
//        buttonChangeInputColor.setToolTipText("Change input color");
//        buttonChangeInputColor.addActionListener(e -> {
//            Color color = JColorChooser.showDialog(null, "Choose a Color", textAreaChatLog.getForeground());
//            if (color != null) {
//                textAreaChatLog.setForeground(color);
//            }
//        });
//        buttonChangeInputColor.setMaximumSize(new Dimension(50, 25));
//        buttonChangeInputColor.setMinimumSize(new Dimension(50, 25));
//        buttonChangeInputColor.setPreferredSize(new Dimension(50, 25));
//
//        buttonSoundOptions.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/volume-on.png")))); // NOI18N
//        buttonSoundOptions.setToolTipText("Sound options");
//        buttonSoundOptions.setMaximumSize(new Dimension(50, 25));
//        buttonSoundOptions.setMinimumSize(new Dimension(50, 25));
//        buttonSoundOptions.setPreferredSize(new Dimension(50, 25));
//        buttonSoundOptions.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!MakeSound.isIncluded()) {
//                    MakeSound.off();
//                    buttonSoundOptions.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/volume-off.png")))); // NOI18N
//                } else {
//                    MakeSound.on();
//                    buttonSoundOptions.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/volume-on.png")))); //
//                }
//            }
//        });
//
//        buttonChatLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/notes.png")))); // NOI18N
//        buttonChatLog.setToolTipText("Save chat log");
//        buttonChatLog.setMaximumSize(new Dimension(50, 25));
//        buttonChatLog.setMinimumSize(new Dimension(50, 25));
//        buttonChatLog.setPreferredSize(new Dimension(50, 25));
//        buttonChatLog.addActionListener(e -> saveToFile());
//
//        buttonMoveToSystemTray.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/moving-truck.png")))); // NOI18N
//        buttonMoveToSystemTray.setToolTipText("Move to system tray");
//        buttonMoveToSystemTray.setMaximumSize(new Dimension(50, 25));
//        buttonMoveToSystemTray.setMinimumSize(new Dimension(50, 25));
//        buttonMoveToSystemTray.setPreferredSize(new Dimension(50, 25));
//        buttonMoveToSystemTray.addActionListener(e -> {
//            try {
//                moveToSystemTray();
//            } catch (IOException | AWTException ioException) {
//                ioException.printStackTrace();
//            }
//        });
//
//        buttonSend.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/send (3).png")))); // NOI18N
//        buttonSend.setToolTipText("Send message");
//        buttonSend.setText("Send");
//        buttonSend.setMaximumSize(new Dimension(85, 25));
//        buttonSend.setMinimumSize(new Dimension(85, 25));
//        buttonSend.setPreferredSize(new Dimension(85, 25));
//        buttonSend.addActionListener(e -> {
//            if (!textFieldUserInputMessage.getText().equals("")) {
//                if (radioButtonCheckPrivateOrNot) {
//                    if (listUserOnline.isSelectedIndex(listUserOnline.getSelectedIndex())) {
//                        client.sendPrivateMessageOnServer(new String[]{listUserOnline.getSelectedValue(), textFieldUserInputMessage.getText()});
//                    } else {
//                        errorDialogWindow("Select a user in the list");
//                    }
//                } else {
//                    client.sendMessageOnServer(textFieldUserInputMessage.getText());
//                }
//                textFieldUserInputMessage.setText("");
//            }
//        });
//
//        textFieldUserInputMessage.setToolTipText("Input message");
//        textFieldUserInputMessage.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (!textFieldUserInputMessage.getText().equals("") && e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    if (radioButtonCheckPrivateOrNot) {
//                        if (listUserOnline.isSelectedIndex(listUserOnline.getSelectedIndex())) {
//                            client.sendPrivateMessageOnServer(new String[]{listUserOnline.getSelectedValue(), textFieldUserInputMessage.getText()});
//                        } else {
//                            errorDialogWindow("Select a user in the list");
//                        }
//                    } else {
//                        client.sendMessageOnServer(textFieldUserInputMessage.getText());
//                    }
//                    textFieldUserInputMessage.setText("");
//                }
//            }
//        });
//
//        listUserOnline.setToolTipText("User list online");
//        listUserOnline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        scrollPanelForUserListOnline.setViewportView(listUserOnline);
//
//        buttonConnectToServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/connection.png")))); // NOI18N
//        buttonConnectToServer.setText("Connect to server");
//        buttonConnectToServer.addActionListener(e -> {
//            if (client.isDatabaseConnected()) {
//                client.connectToServer();
//            } else {
//                errorDialogWindow("Not connected database");
//            }
//        });
//        buttonConnectToServer.setToolTipText("Connect to server");
//
//        textAreaChatLog.setEditable(false);
//        textAreaChatLog.setColumns(20);
//        textAreaChatLog.setRows(5);
//        textAreaChatLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
//        textAreaChatLog.setToolTipText("Chat log");
//        scrollPanelForChatLog.setViewportView(textAreaChatLog);
//
//        buttonDisconnectToServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/disconnected.png")))); // NOI18N
//        buttonDisconnectToServer.setText("Disconnect to server");
//        buttonDisconnectToServer.addActionListener(e -> client.disableClient());
//
//        menuDatabase.setText("Database");
//
//        menuItemLogin.setText("Login");
//        menuItemLogin.addActionListener(e -> userLogin());
//        menuDatabase.add(menuItemLogin);
//
//        menuItemRegistration.setText("Registration");
//        menuItemRegistration.addActionListener(e -> userRegistration());
//        menuDatabase.add(menuItemRegistration);
//
//        menuOthers.setText("Others");
//
//        menuItemChangeUsername.setText("Change username");
//        menuItemChangeUsername.addActionListener(e -> {
//            if (client.isDatabaseConnected()) {
//                try {
//                    client.changeUsername();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
//            }
//        });
//        menuOthers.add(menuItemChangeUsername);
//
//        menuBar.add(menuDatabase);
//        menuBar.add(menuOthers);
//
//        setJMenuBar(menuBar);
//
//        GroupLayout layout = new GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addGap(5, 5, 5)
//                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addComponent(radioButtonSendMessageToAll)
//                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
//                                                .addComponent(radioButtonSendPrivateMessageToSelectedUser)
//                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                                .addComponent(buttonClearChatLog, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 0, 0)
//                                                .addComponent(buttonChangeInputColor, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 0, 0)
//                                                .addComponent(buttonSoundOptions, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 0, 0)
//                                                .addComponent(buttonChatLog, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 0, 0)
//                                                .addComponent(buttonMoveToSystemTray, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 0, 0)
//                                                .addComponent(buttonSend, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 5, Short.MAX_VALUE))
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                                        .addComponent(textFieldUserInputMessage)
//                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                                                                .addComponent(scrollPanelForChatLog)
//                                                                .addGap(5, 5, 5)
//                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
//                                                                        .addComponent(scrollPanelForUserListOnline, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
//                                                                        .addComponent(buttonConnectToServer, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
//                                                                        .addComponent(buttonDisconnectToServer, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))))
//                                                .addGap(5, 5, 5))))
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                                .addGap(5, 5, 5)
//                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addComponent(buttonConnectToServer, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
//                                                .addGap(5, 5, 5)
//                                                .addComponent(buttonDisconnectToServer)
//                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                                .addComponent(scrollPanelForUserListOnline, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
//                                        .addComponent(scrollPanelForChatLog))
//                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(textFieldUserInputMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                .addGap(5, 5, 5)
//                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                                        .addComponent(radioButtonSendMessageToAll)
//                                        .addComponent(radioButtonSendPrivateMessageToSelectedUser)
//                                        .addComponent(buttonClearChatLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(buttonChangeInputColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(buttonSoundOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(buttonChatLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(buttonMoveToSystemTray, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//                                        .addComponent(buttonSend, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//                                .addGap(5, 5, 5))
//        );
//
//        setVisible(true);
//        pack();

        buttonGroup = new javax.swing.ButtonGroup();
        radioButtonSendMessageToAll = new javax.swing.JRadioButton();
        radioButtonSendPrivateMessageToSelectedUser = new javax.swing.JRadioButton();
        buttonChangeName = new javax.swing.JButton();
        buttonChangeInputColor = new javax.swing.JButton();
        buttonSoundOptions = new javax.swing.JButton();
        buttonChatLog = new javax.swing.JButton();
        buttonMoveToSystemTray = new javax.swing.JButton();
        buttonSend = new javax.swing.JButton();
        textFieldUserInputMessage = new javax.swing.JTextField();
        scrollPanelForUserListOnline = new javax.swing.JScrollPane();
        listUserOnline = new javax.swing.JList<>();
        buttonConnectionToServer = new javax.swing.JButton();
        scrollPanelForChatLog = new javax.swing.JScrollPane();
        textAreaChatLog = new javax.swing.JTextArea();
        buttonRegistration = new javax.swing.JButton();
        buttonSignIn = new javax.swing.JButton();
        buttonSignOut = new javax.swing.JButton();
        buttonDisconnectToServer = new javax.swing.JButton();

        setTitle("Network chat");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1100, 400));
        setPreferredSize(new java.awt.Dimension(1100, 400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isClientConnected()) {
                    client.disableClient();
                }
                System.exit(0);
            }
        });

        try {
            setIconImage(ImageIO.read(new File("default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonGroup.add(radioButtonSendMessageToAll);
        radioButtonSendMessageToAll.setSelected(true);
        radioButtonSendMessageToAll.setText("Send message to all");
        radioButtonSendMessageToAll.addActionListener(e -> radioButtonCheckPrivateOrNot = false);

        buttonGroup.add(radioButtonSendPrivateMessageToSelectedUser);
        radioButtonSendPrivateMessageToSelectedUser.setText("Send private message to selected user");
        radioButtonSendPrivateMessageToSelectedUser.addActionListener(e -> radioButtonCheckPrivateOrNot = true);

        buttonChangeName.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/change-name.png")))); // NOI18N
        buttonChangeName.setToolTipText("Change name");
        buttonChangeName.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonChangeName.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonChangeName.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonChangeName.addActionListener(e -> {
            if (client.isDatabaseConnected()) {
                try {
                    client.changeUsername();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                errorDialogWindow("Connect to database to change nickname");
            }
        });

        buttonChangeInputColor.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/color-wheel.png")))); // NOI18N
        buttonChangeInputColor.setToolTipText("Change input color");
        buttonChangeInputColor.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonChangeInputColor.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonChangeInputColor.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonChangeInputColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose a Color", textAreaChatLog.getForeground());
            if (color != null) {
                textAreaChatLog.setForeground(color);
            }
        });

        buttonSoundOptions.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/volume-on.png")))); // NOI18N
        buttonSoundOptions.setToolTipText("Sound options");
        buttonSoundOptions.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonSoundOptions.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonSoundOptions.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonSoundOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MakeSound.isIncluded()) {
                    MakeSound.off();
                    buttonSoundOptions.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/volume-off.png")))); // NOI18N
                } else {
                    MakeSound.on();
                    buttonSoundOptions.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/volume-on.png")))); //
                }
            }
        });

        buttonChatLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/notes.png")))); // NOI18N
        buttonChatLog.setToolTipText("Chat log");
        buttonChatLog.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonChatLog.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonChatLog.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonChatLog.addActionListener(e -> saveToFile());

        buttonMoveToSystemTray.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/moving-truck.png")))); // NOI18N
        buttonMoveToSystemTray.setToolTipText("Move to system tray");
        buttonMoveToSystemTray.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonMoveToSystemTray.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonMoveToSystemTray.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonMoveToSystemTray.addActionListener(e -> {
            try {
                moveToSystemTray();
            } catch (IOException | AWTException ioException) {
                ioException.printStackTrace();
            }
        });

        buttonSend.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/send (3).png")))); // NOI18N
        buttonSend.setText("Send");
        buttonSend.setToolTipText("Send message");
        buttonSend.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonSend.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonSend.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonSend.addActionListener(e -> {
            if (!textFieldUserInputMessage.getText().equals("")) {
                if (radioButtonCheckPrivateOrNot) {
                    if (listUserOnline.isSelectedIndex(listUserOnline.getSelectedIndex())) {
                        client.sendPrivateMessageOnServer(listUserOnline.getSelectedValue(), textFieldUserInputMessage.getText());
                    } else {
                        errorDialogWindow("Please select a user from the list, otherwise you will not be able to send a private message");
                    }
                } else {
                    client.sendMessageOnServer(textFieldUserInputMessage.getText());
                }
                textFieldUserInputMessage.setText("");
            }
        });

        textFieldUserInputMessage.setToolTipText("Input message");
        textFieldUserInputMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!textFieldUserInputMessage.getText().equals("") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (radioButtonCheckPrivateOrNot) {
                        if (listUserOnline.isSelectedIndex(listUserOnline.getSelectedIndex())) {
                            client.sendPrivateMessageOnServer(listUserOnline.getSelectedValue(), textFieldUserInputMessage.getText());
                        } else {
                            errorDialogWindow("Please select a user from the list, otherwise you will not be able to send a private message");
                        }
                    } else {
                        client.sendMessageOnServer(textFieldUserInputMessage.getText());
                    }
                    textFieldUserInputMessage.setText("");
                }
            }
        });

        listUserOnline.setToolTipText("User list online");
        listUserOnline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPanelForUserListOnline.setViewportView(listUserOnline);

        textAreaChatLog.setEditable(false);
        textAreaChatLog.setColumns(20);
        textAreaChatLog.setRows(5);
        textAreaChatLog.setToolTipText("Chat log");
        textAreaChatLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPanelForChatLog.setViewportView(textAreaChatLog);

        buttonRegistration.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/registration.png")))); // NOI18N
        buttonRegistration.setText("Registration");
        buttonRegistration.setToolTipText("Database registration");
        buttonRegistration.setMaximumSize(new java.awt.Dimension(125, 25));
        buttonRegistration.setMinimumSize(new java.awt.Dimension(125, 25));
        buttonRegistration.setPreferredSize(new java.awt.Dimension(125, 25));
        buttonRegistration.addActionListener(e -> {
            if (!client.isDatabaseConnected()) {
                userRegistration();
            } else {
                errorDialogWindow("Log out of the database to register");
            }
        });

        buttonSignIn.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/sign-in.png")))); // NOI18N
        buttonSignIn.setText("Sign in");
        buttonSignIn.setToolTipText("Database sign in");
        buttonSignIn.setMaximumSize(new java.awt.Dimension(100, 25));
        buttonSignIn.setMinimumSize(new java.awt.Dimension(100, 25));
        buttonSignIn.setPreferredSize(new java.awt.Dimension(100, 25));
        buttonSignIn.addActionListener(e -> {
            if (!client.isDatabaseConnected()) {
                userLogin();
            } else {
                errorDialogWindow("You are already connected to the database");
            }
        });

        buttonSignOut.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/sign-out.png")))); // NOI18N
        buttonSignOut.setText("Sign out");
        buttonSignOut.setToolTipText("Database sign out");
        buttonSignOut.setMaximumSize(new java.awt.Dimension(100, 25));
        buttonSignOut.setMinimumSize(new java.awt.Dimension(100, 25));
        buttonSignOut.setPreferredSize(new java.awt.Dimension(100, 25));
        buttonSignOut.addActionListener(e -> {
            if (client.isDatabaseConnected()) {
                client.setDatabaseConnected(false);

            } else {
                errorDialogWindow("You are not connected to the database");
            }
        });

        buttonConnectionToServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/connection.png")))); // NOI18N
        buttonConnectionToServer.setText("Connect");
        buttonConnectionToServer.setToolTipText("Connect to server");
        buttonConnectionToServer.addActionListener(e -> {
            if (client.isDatabaseConnected()) {
                client.connectToServer();
            } else {
                errorDialogWindow("Connect to database to login to server");
            }
        });

        buttonDisconnectToServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/disconnected.png")))); // NOI18N
        buttonDisconnectToServer.setText("Disconnect");
        buttonDisconnectToServer.setToolTipText("Disconnect to server");
        buttonDisconnectToServer.addActionListener(e -> {
            if (client.isClientConnected()) {
                client.disableClient();
            } else {
                errorDialogWindow("You are already disconnected from the server");
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(radioButtonSendMessageToAll)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(radioButtonSendPrivateMessageToSelectedUser)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonSignIn)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSignOut)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonRegistration)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonChangeInputColor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSoundOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonChatLog, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonMoveToSystemTray, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(textFieldUserInputMessage)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(scrollPanelForChatLog)
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(buttonDisconnectToServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(buttonConnectionToServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(scrollPanelForUserListOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonConnectionToServer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonDisconnectToServer)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollPanelForUserListOnline, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                                        .addComponent(scrollPanelForChatLog))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldUserInputMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonChangeInputColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonSoundOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(radioButtonSendMessageToAll)
                                                .addComponent(radioButtonSendPrivateMessageToSelectedUser)
                                                .addComponent(buttonSend))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(buttonRegistration)
                                                .addComponent(buttonSignIn)
                                                .addComponent(buttonSignOut))
                                        .addComponent(buttonChatLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonMoveToSystemTray, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5))
        );
        pack();
        setVisible(true);
    }

    /**
     * Добавляет сообщение в чат
     *
     * @param text сообщение которое будет добавлено в чат
     */
    protected void addMessage(String text) {
        textAreaChatLog.append("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + text);
        MakeSound.playSound("new-message.wav");
    }

    /**
     * Метод обновляющий списо имен подлючившихся пользователей
     *
     * @param allUserNames список пользователей
     */
    protected void refreshListUsers(Set<String> allUserNames) {
        StringBuilder text = new StringBuilder();
        for (String user : allUserNames) {
            text.append(user).append("\n");
        }
        String[] strings = text.toString().split("\n");
        listUserOnline.setModel(new AbstractListModel<>() {
            public int getSize() {
                return allUserNames.size();
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
    }

    /**
     * Вызывает окно для ввода адреса сервера
     *
     * @return возращает введенное пользователем строку
     */
    protected String getServerAddress() {
        while (true) {
            return JOptionPane.showInputDialog(this, "Enter the server address:", "Server address input", JOptionPane.QUESTION_MESSAGE).trim();
        }
    }

    /**
     * Вызывает окно для ввода порт сервера
     *
     * @return возращает введенное пользователем число
     */
    protected int getPort() {
        while (true) {
            String port = JOptionPane.showInputDialog(this, "Enter the server port:", "Server port input", JOptionPane.QUESTION_MESSAGE);
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                MakeSound.playSound("failed.wav");
                JOptionPane.showMessageDialog(this, "Incorrect server port entered. Try again.", "Server port input error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Вызывает окна для ввода имени пользователя
     *
     * @return возращает введенное пользователем строку
     */
    protected String getUserName() {
        return JOptionPane.showInputDialog(this, "Enter your username:", "Username input", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Вызывает окно ошибки с заданным текстом
     *
     * @param text ошибка с заданным текстом
     */
    protected void errorDialogWindow(String text) {
        MakeSound.playSound("failed.wav");
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Вызывает окно куда можно сохранить историю сообщении
     */
    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(new JButton("Save")) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file != null) {
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getParentFile(), file.getName() + ".txt");
                }
                try {
                    textAreaChatLog.write(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void moveToSystemTray() throws IOException, AWTException {
        BufferedImage Icon = ImageIO.read(new File("default.png"));
        final TrayIcon trayIcon = new TrayIcon(Icon, "Network chat");
        setVisible(false);
        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    setVisible(true);
                    setExtendedState(JFrame.NORMAL);
                    systemTray.remove(trayIcon);
                }
            }
        });
    }

    protected void userLogin() {
        JPasswordField passwordField = new JPasswordField();
        JTextField textFieldUsername = new JTextField();
        int usernameDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, textFieldUsername, "Enter Username", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        int passwordDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (passwordDialog == JOptionPane.OK_OPTION && usernameDialog == JOptionPane.OK_OPTION) {
            if (SQLService.getNicknameByLoginAndPassword(textFieldUsername.getText(), String.valueOf(passwordField.getPassword())) != null) {
                JOptionPane.showMessageDialog(ClientGuiView.this, "You are successfully logged in", "Sign in", JOptionPane.INFORMATION_MESSAGE);
                client.setUserName(textFieldUsername.getText());
                client.setDatabaseConnected(true);
            } else {
                JOptionPane.showMessageDialog(ClientGuiView.this, "Failed to login", "Sign in", JOptionPane.ERROR_MESSAGE);
                client.setDatabaseConnected(false);
            }
        }
    }

    protected void userRegistration() {
        JPasswordField passwordField = new JPasswordField();
        JTextField textFieldUsername = new JTextField();
        int usernameDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, textFieldUsername, "Enter Username", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        int passwordDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (passwordDialog == JOptionPane.OK_OPTION && usernameDialog == JOptionPane.OK_OPTION) {
            if (SQLService.registration(textFieldUsername.getText(), String.valueOf(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(ClientGuiView.this, "You are successfully registration", "Registration", JOptionPane.INFORMATION_MESSAGE);
                client.setUserName(textFieldUsername.getText());
            } else {
                JOptionPane.showMessageDialog(ClientGuiView.this, "Failed to registration", "Registration", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
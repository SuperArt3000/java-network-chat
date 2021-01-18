package client;

import authorization.Login;
import authorization.Registration;
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
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

/**
 * @author Zurbaevi Nika
 */
public class ClientGuiView extends JFrame {

    private final ClientGuiController client;

    private JButton buttonChangeInputColor;
    private JButton buttonChangeName;
    private JButton buttonChatLog;
    private JButton buttonConnectionToServer;
    private JButton buttonDisconnectToServer;
    private ButtonGroup buttonGroup;
    private JButton buttonMoveToSystemTray;
    private JButton buttonRegistration;
    private JButton buttonSend;
    private JButton buttonSignIn;
    private JButton buttonSignOut;
    private JButton buttonSoundOptions;
    private JList<String> listUserOnline;
    private JRadioButton radioButtonSendMessageToAll;
    private JRadioButton radioButtonSendPrivateMessageToSelectedUser;
    private JScrollPane scrollPanelForChatLog;
    private JScrollPane scrollPanelForUserListOnline;
    private JTextArea textAreaChatLog;
    private JTextField textFieldUserInputMessage;

    private boolean radioButtonCheckPrivateOrNot;

    public ClientGuiView(ClientGuiController clientGuiController) {
        this.client = clientGuiController;

        SQLService.getInstance();

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGuiView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    protected void initComponents() {
        buttonGroup = new ButtonGroup();
        radioButtonSendMessageToAll = new JRadioButton();
        radioButtonSendPrivateMessageToSelectedUser = new JRadioButton();
        buttonChangeName = new JButton();
        buttonChangeInputColor = new JButton();
        buttonSoundOptions = new JButton();
        buttonChatLog = new JButton();
        buttonMoveToSystemTray = new JButton();
        buttonSend = new JButton();
        textFieldUserInputMessage = new JTextField();
        scrollPanelForUserListOnline = new JScrollPane();
        listUserOnline = new JList<>();
        buttonConnectionToServer = new JButton();
        scrollPanelForChatLog = new JScrollPane();
        textAreaChatLog = new JTextArea();
        buttonRegistration = new JButton();
        buttonSignIn = new JButton();
        buttonSignOut = new JButton();
        buttonDisconnectToServer = new JButton();

        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(740, 400));
        setPreferredSize(new java.awt.Dimension(740, 400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isClientConnected()) {
                    client.disableClient();
                }
                try {
                    if (SQLService.isConnected()) {
                        SQLService.closeConnection();
                    }
                } catch (SQLException sqlException) {
                    errorDialogWindow(sqlException.getMessage());
                }
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);

        try {
            setIconImage(ImageIO.read(new File("default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonGroup.add(radioButtonSendMessageToAll);
        radioButtonSendMessageToAll.setSelected(true);
        radioButtonSendMessageToAll.setEnabled(false);
        radioButtonSendMessageToAll.setText("Send message");
        radioButtonSendMessageToAll.setToolTipText("Send message to all");
        radioButtonSendMessageToAll.addActionListener(e -> radioButtonCheckPrivateOrNot = false);

        buttonGroup.add(radioButtonSendPrivateMessageToSelectedUser);
        radioButtonSendPrivateMessageToSelectedUser.setText("Send private message");
        radioButtonSendPrivateMessageToSelectedUser.setToolTipText("Send private message to selected user");
        radioButtonSendPrivateMessageToSelectedUser.setEnabled(false);
        radioButtonSendPrivateMessageToSelectedUser.addActionListener(e -> radioButtonCheckPrivateOrNot = true);

        buttonChangeName.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/change-name.png")))); // NOI18N
        buttonChangeName.setToolTipText("Change name");
        buttonChangeName.setEnabled(false);
        buttonChangeName.addActionListener(e -> {
            client.changeNickname();
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

        buttonChatLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/save-log.png")))); // NOI18N
        buttonChatLog.setToolTipText("Chat log");
        buttonChatLog.addActionListener(e -> saveToFile());

        buttonMoveToSystemTray.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/move-tray.png")))); // NOI18N
        buttonMoveToSystemTray.setToolTipText("Move to system tray");
        buttonMoveToSystemTray.addActionListener(e -> {
            moveToSystemTray();
        });

        buttonSend.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/send-message.png")))); // NOI18N
        buttonSend.setText("Send");
        buttonSend.setToolTipText("Send message");
        buttonSend.setEnabled(false);
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
        textFieldUserInputMessage.setEnabled(false);
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
        buttonRegistration.setText("");
        buttonRegistration.setToolTipText("Database registration");
        buttonRegistration.addActionListener(e -> {
            if (!client.isDatabaseConnected()) {
                Registration registration = new Registration(this);
                registration.setVisible(true);
                if (registration.isSucceeded()) {
                    client.setNickname(registration.getNickname());
                }
            }
        });

        buttonSignIn.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/sign-in.png")))); // NOI18N
        buttonSignIn.setText("");
        buttonSignIn.setToolTipText("Database sign in");
        buttonSignIn.addActionListener(e -> {
            if (!client.isDatabaseConnected()) {
                Login loginDialog = new Login(this);
                loginDialog.setVisible(true);
                if (loginDialog.isSucceeded()) {
                    client.setNickname(loginDialog.getNickname());
                    client.setDatabaseConnected(true);
                    buttonSignIn.setEnabled(false);
                    buttonSignOut.setEnabled(true);
                    buttonConnectionToServer.setEnabled(true);
                    buttonRegistration.setEnabled(false);
                }
            }
        });

        buttonSignOut.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/sign-out.png")))); // NOI18N
        buttonSignOut.setText("");
        buttonSignOut.setToolTipText("Database sign out");
        buttonSignOut.setEnabled(false);
        buttonSignOut.addActionListener(e -> {
            if (client.isDatabaseConnected()) {
                client.setDatabaseConnected(false);
                buttonSignOut.setEnabled(false);
                buttonSignIn.setEnabled(true);
                radioButtonSendMessageToAll.setEnabled(false);
                radioButtonSendPrivateMessageToSelectedUser.setEnabled(false);
                buttonSend.setEnabled(false);
                buttonConnectionToServer.setEnabled(false);
                buttonDisconnectToServer.setEnabled(false);
                buttonRegistration.setEnabled(true);
                buttonChangeName.setEnabled(false);
                if (client.isClientConnected()) {
                    client.disableClient();
                }
            }
        });

        buttonConnectionToServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/connection.png")))); // NOI18N
        buttonConnectionToServer.setText("Connect");
        buttonConnectionToServer.setToolTipText("Connect to server");
        buttonConnectionToServer.setEnabled(false);
        buttonConnectionToServer.addActionListener(e -> {
            if (client.isDatabaseConnected()) {
                client.connectToServer();
                if (client.isClientConnected()) {
                    buttonDisconnectToServer.setEnabled(true);
                    buttonConnectionToServer.setEnabled(false);
                    buttonChangeName.setEnabled(true);
                    textFieldUserInputMessage.setEnabled(true);
                    radioButtonSendMessageToAll.setEnabled(true);
                    radioButtonSendPrivateMessageToSelectedUser.setEnabled(true);
                    buttonSend.setEnabled(true);
                }
            }
        });

        buttonDisconnectToServer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/disconnected.png")))); // NOI18N
        buttonDisconnectToServer.setText("Disconnect");
        buttonDisconnectToServer.setToolTipText("Disconnect to server");
        buttonDisconnectToServer.setEnabled(false);
        buttonDisconnectToServer.addActionListener(e -> {
            if (client.isClientConnected()) {
                client.disableClient();
                if (!client.isClientConnected()) {
                    buttonConnectionToServer.setEnabled(true);
                    buttonDisconnectToServer.setEnabled(false);
                    buttonChangeName.setEnabled(false);
                    textFieldUserInputMessage.setEnabled(false);
                    radioButtonSendMessageToAll.setEnabled(false);
                    radioButtonSendPrivateMessageToSelectedUser.setEnabled(false);
                    buttonSend.setEnabled(false);
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(radioButtonSendMessageToAll)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(radioButtonSendPrivateMessageToSelectedUser)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonSignIn)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSignOut)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonRegistration)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonChangeName, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonChangeInputColor, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSoundOptions, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonChatLog, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonMoveToSystemTray, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(buttonSend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(textFieldUserInputMessage)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(scrollPanelForChatLog)
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(buttonDisconnectToServer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(buttonConnectionToServer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(scrollPanelForUserListOnline, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))))
                                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(buttonConnectionToServer, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonDisconnectToServer)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollPanelForUserListOnline, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                                        .addComponent(scrollPanelForChatLog))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldUserInputMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonChangeInputColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonSoundOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(radioButtonSendMessageToAll)
                                                .addComponent(radioButtonSendPrivateMessageToSelectedUser)
                                                .addComponent(buttonSend))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(buttonRegistration)
                                                .addComponent(buttonSignIn)
                                                .addComponent(buttonSignOut))
                                        .addComponent(buttonChatLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonMoveToSystemTray, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonChangeName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5))
        );
        pack();
        setVisible(true);
    }

    protected void addMessage(String text) {
        textAreaChatLog.append("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + text);
        MakeSound.playSound("new-message.wav");
    }

    protected void refreshListUsers(Set<String> allUserNicknames) {
        StringBuilder text = new StringBuilder();
        for (String user : allUserNicknames) {
            text.append(user).append("\n");
        }
        String[] strings = text.toString().split("\n");
        listUserOnline.setModel(new AbstractListModel<>() {
            public int getSize() {
                return allUserNicknames.size();
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
    }

    protected String getServerAddress() {
        while (true) {
            return JOptionPane.showInputDialog(this, "Enter the server address:", "Server address input", JOptionPane.QUESTION_MESSAGE).trim();
        }
    }

    protected int getPort() {
        while (true) {
            String port = JOptionPane.showInputDialog(this, "Enter the server port:", "Server port input", JOptionPane.QUESTION_MESSAGE);
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Incorrect server port entered. Try again.", "Server port input error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected String getNickname() {
        return JOptionPane.showInputDialog(this, "Enter your username:", "Username input", JOptionPane.QUESTION_MESSAGE);
    }

    protected void errorDialogWindow(String text) {

        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

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

    private void moveToSystemTray() {
        try {
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
        } catch (IOException | AWTException e) {
            errorDialogWindow(e.getMessage());
        }
    }
}

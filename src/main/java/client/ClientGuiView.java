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
        setMinimumSize(new java.awt.Dimension(1100, 400));
        setPreferredSize(new java.awt.Dimension(1100, 400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isClientConnected()) {
                    client.disableClient();
                }
                SQLService.closeConnection();
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

        buttonChatLog.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/save-log.png")))); // NOI18N
        buttonChatLog.setToolTipText("Chat log");
        buttonChatLog.setMaximumSize(new java.awt.Dimension(50, 25));
        buttonChatLog.setMinimumSize(new java.awt.Dimension(50, 25));
        buttonChatLog.setPreferredSize(new java.awt.Dimension(50, 25));
        buttonChatLog.addActionListener(e -> saveToFile());

        buttonMoveToSystemTray.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/move-tray.png")))); // NOI18N
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

        buttonSend.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/send-message.png")))); // NOI18N
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
                MakeSound.playSound("failed.wav");
                JOptionPane.showMessageDialog(this, "Incorrect server port entered. Try again.", "Server port input error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected String getNickname() {
        return JOptionPane.showInputDialog(this, "Enter your username:", "Username input", JOptionPane.QUESTION_MESSAGE);
    }

    protected void errorDialogWindow(String text) {
        MakeSound.playSound("failed.wav");
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
        JTextField textFieldNickname = new JTextField();
        int usernameDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, textFieldNickname, "Enter Nickname", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        int passwordDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (passwordDialog == JOptionPane.OK_OPTION && usernameDialog == JOptionPane.OK_OPTION) {
            if (SQLService.getNicknameByLoginAndPassword(textFieldNickname.getText(), String.valueOf(passwordField.getPassword())) != null) {
                JOptionPane.showMessageDialog(ClientGuiView.this, "You are successfully logged in", "Sign in", JOptionPane.INFORMATION_MESSAGE);
                client.setNickname(textFieldNickname.getText());
                client.setDatabaseConnected(true);
            } else {
                JOptionPane.showMessageDialog(ClientGuiView.this, "Failed to login", "Sign in", JOptionPane.ERROR_MESSAGE);
                client.setDatabaseConnected(false);
            }
        }
    }

    protected void userRegistration() {
        JPasswordField passwordField = new JPasswordField();
        JTextField textFieldNickname = new JTextField();
        int usernameDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, textFieldNickname, "Enter Nickname", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        int passwordDialog = JOptionPane.showConfirmDialog(ClientGuiView.this, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (passwordDialog == JOptionPane.OK_OPTION && usernameDialog == JOptionPane.OK_OPTION) {
            if (SQLService.registration(textFieldNickname.getText(), String.valueOf(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(ClientGuiView.this, "You are successfully registration", "Registration", JOptionPane.INFORMATION_MESSAGE);
                client.setNickname(textFieldNickname.getText());
            } else {
                JOptionPane.showMessageDialog(ClientGuiView.this, "Failed to registration", "Registration", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
